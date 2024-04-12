package ca.courseplanner.model;

import ca.courseplanner.model.sort.SortCourseByCatalogNumber;
import ca.courseplanner.model.sort.SortCourseOfferingBySemesterCode;
import ca.courseplanner.model.sort.SortDepartmentByName;
import ca.courseplanner.model.sort.SortOfferingSectionByType;

import java.util.*;

import static ca.courseplanner.model.CsvFileReader.*;

public class DataFacade {
    private List<OfferingData> offeringDataList;
    private List<Department> departmentList;

    public DataFacade(List<String[]> data) {
        this.offeringDataList = new ArrayList<>();
        processCsvData(data);
        processDepartments();
        processCoursesInDepartment();
    }

    public List<OfferingData> getOfferingDataList() {
        return offeringDataList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    private void processCsvData(List<String[]> data) {
        for (String[] rowData : data) {
            String semester = rowData[SEMESTER_INDEX].trim();
            String subjectName = rowData[SUBJECT_NAME_INDEX].trim();
            String catalogNumber = rowData[CATALOG_NUMBER_INDEX].trim();
            String location = rowData[LOCATION_INDEX].trim();
            int enrollmentCap = Integer.parseInt(rowData[ENROLLMENT_CAP_INDEX]);
            int enrollmentTotal = Integer.parseInt(rowData[ENROLLMENT_TOTAL_INDEX]);
            String instructor = (rowData[INSTRUCTOR_INDEX].trim().equals("<null>")
                    || rowData[INSTRUCTOR_INDEX].trim().equals("(null)"))
                    ? "" : rowData[INSTRUCTOR_INDEX].trim();
            String component = rowData[COMPONENT_INDEX].trim();

            OfferingData existingOffering = findExistingOffering(subjectName, catalogNumber,
                    semester, location, component);
            if (existingOffering != null) {
                existingOffering.setEnrollmentCap(existingOffering.getEnrollmentCap() + enrollmentCap);
                existingOffering.setEnrollmentTotal(existingOffering.getEnrollmentTotal() + enrollmentTotal);
                addInstructorsIfNotExisted(existingOffering, instructor);
            } else {
                OfferingData offeringData = new OfferingData(semester, subjectName,
                        catalogNumber, location,
                        enrollmentCap, component, enrollmentTotal, instructor);
                offeringDataList.add(offeringData);
            }
        }
        sortByComponent();
        sortByLocation();
        sortBySemester();
        sortByCourse();
    }

    public void processDepartments() {
        departmentList = new ArrayList<>();
        for (OfferingData offeringData : offeringDataList) {
            String departmentName = offeringData.getSubjectName();
            boolean departmentExists = false;

            for (Department department : departmentList) {
                if (department.getName().equals(departmentName)) {
                    departmentExists = true;
                    break;
                }
            }

            if (!departmentExists) {
                departmentList.add(new Department(departmentName));
            }
        }
    }

    private void processCoursesInDepartment() {
        for (OfferingData offeringData : offeringDataList) {
            processOfferingData(offeringData);
        }
    }

    public void processOfferingData(OfferingData data) {
        Department department = findDepartment(data.getSubjectName());
        if (department == null) {
            department = new Department(data.getSubjectName());
            departmentList.add(department);
            departmentList.sort(new SortDepartmentByName());
        }

        Course course = findCourse(department, data.getCatalogNumber());
        if (course == null) {
            course = new Course(data.getCatalogNumber());
            department.addCourse(course);
            List<Course> courseList = department.getCourseList();
            courseList.sort(new SortCourseByCatalogNumber());
        }

        CourseOffering offering = findOffering(course, data);
        boolean isNewOffering = false;
        if (offering == null) {
            offering = new CourseOffering(generateOfferingKey(data), data.getLocation(),
                    data.getInstructor(), data.getTerm(), data.getSemesterCode(), data.getYear());
            isNewOffering = true;
            course.addCourseOffering(offering);
            List<CourseOffering> courseOfferingList = course.getCourseOfferings();
            courseOfferingList.sort(new SortCourseOfferingBySemesterCode());
        }

        boolean newSectionUpdated = updateOrCreateSection(offering, data);
        if (isNewOffering || newSectionUpdated) {
            Date date = new Date();
            course.notifyObservers(date + ": Added section " + data.getComponent() +
                    " with enrollment (" + data.getEnrollmentTotal() + " / " + data.getEnrollmentCap() + ") " +
                    "to offering " + data.getTerm() + " " + data.getYear());
        }
    }

    private boolean updateOrCreateSection(CourseOffering offering, OfferingData data) {
        OfferingSection existingSection = findSection(offering, data.getComponent());
        if (existingSection != null) {
            int initialCap = existingSection.getEnrollmentCap();
            int initialTotal = existingSection.getEnrollmentTotal();

            existingSection.setEnrollmentCap(initialCap + data.getEnrollmentCap());
            existingSection.setEnrollmentTotal(initialTotal + data.getEnrollmentTotal());

            return initialCap != existingSection.getEnrollmentCap() ||
                    initialTotal != existingSection.getEnrollmentTotal();
        } else {
            OfferingSection newSection = new OfferingSection(data.getComponent(),
                    data.getEnrollmentCap(), data.getEnrollmentTotal());
            offering.addOfferingSection(newSection);
            List<OfferingSection> offeringSectionList = offering.getOfferingSections();
            offeringSectionList.sort(new SortOfferingSectionByType());
            return true;
        }
    }

    public Department findDepartment(String subjectName) {
        for (Department department : departmentList) {
            if (department.getName().equals(subjectName)) {
                return department;
            }
        }
        return null;
    }

    public Course findCourse(Department department, String catalogNumber) {
        for (Course course : department.getCourseList()) {
            if (course.getCatalogNumber().equals(catalogNumber)) {
                return course;
            }
        }
        return null;
    }

    public CourseOffering findOffering(Course course, OfferingData data) {
        String offeringKey = generateOfferingKey(data);
        for (CourseOffering offering : course.getCourseOfferings()) {
            if (offering.getOfferingKey().equals(offeringKey)) {
                return offering;
            }
        }
        return null;
    }

    public OfferingSection findSection(CourseOffering offering, String componentType) {
        for (OfferingSection section : offering.getOfferingSections()) {
            if (section.getType().equals(componentType)) {
                return section;
            }
        }
        return null;
    }

    private void addInstructorsIfNotExisted(OfferingData existingOffering, String instructor) {
        if (instructor.isEmpty()) {
            for (OfferingData offering : offeringDataList) {
                if (offering.getSubjectName().equals(existingOffering.getSubjectName()) &&
                        offering.getCatalogNumber().equals(existingOffering.getCatalogNumber()) &&
                        offering.getSemester().equals(existingOffering.getSemester()) &&
                        offering.getLocation().equals(existingOffering.getLocation()) &&
                        !offering.getInstructor().isEmpty()) {
                    instructor = offering.getInstructor();
                    break;
                }
            }
        }

        for (OfferingData offering : offeringDataList) {
            if (offering.getSubjectName().equals(existingOffering.getSubjectName()) &&
                    offering.getCatalogNumber().equals(existingOffering.getCatalogNumber()) &&
                    offering.getSemester().equals(existingOffering.getSemester()) &&
                    offering.getLocation().equals(existingOffering.getLocation())) {
                String currentInstructorList = getCurrentInstructorList(instructor, offering);
                offering.setInstructor(currentInstructorList);
            }
        }
    }

    private String getCurrentInstructorList(String instructor, OfferingData offering) {
        StringBuilder currentInstructorList = new StringBuilder(offering.getInstructor());
        String[] currentInstructors = currentInstructorList.toString().split(", ");
        String[] newInstructors = instructor.split(", ");
        for (String newInstructor : newInstructors) {
            boolean isInList = false;
            for (String existingInstructor : currentInstructors) {
                if (existingInstructor.equals(newInstructor)) {
                    isInList = true;
                    break;
                }
            }
            if (!isInList) {
                if (!currentInstructorList.isEmpty()) {
                    currentInstructorList.append(", ");
                }
                currentInstructorList.append(newInstructor);
            }
        }
        return currentInstructorList.toString();
    }

    private OfferingData findExistingOffering(String subjectName, String catalogNumber, String semester,
                                              String location, String component) {
        for (OfferingData offering : offeringDataList) {
            if (offering.getSubjectName().equals(subjectName) && offering.getCatalogNumber().equals(catalogNumber)
                    && offering.getSemester().equals(semester) && offering.getLocation().equals(location)
                    && offering.getComponent().equals(component)) {
                return offering;
            }
        }
        return null;
    }

    private String generateOfferingKey(OfferingData data) {
        return data.getSemesterCode() + data.getLocation();
    }

    private void sortByCourse() {
        Comparator<OfferingData> makeCourseSorter = new Comparator<OfferingData>() {
            @Override
            public int compare(OfferingData o1, OfferingData o2) {
                return o1.getCourseName().compareTo(o2.getCourseName());
            }
        };
        offeringDataList.sort(makeCourseSorter);
    }

    private void sortBySemester() {
        Comparator<OfferingData> makeSemesterSorter = new Comparator<OfferingData>() {
            @Override
            public int compare(OfferingData o1, OfferingData o2) {
                return o1.getSemester().compareTo(o2.getSemester());
            }
        };
        offeringDataList.sort(makeSemesterSorter);
    }

    private void sortByLocation() {
        Comparator<OfferingData> makeLocationSorter = new Comparator<OfferingData>() {
            @Override
            public int compare(OfferingData o1, OfferingData o2) {
                return o1.getLocation().compareTo(o2.getLocation());
            }
        };
        offeringDataList.sort(makeLocationSorter);
    }

    private void sortByComponent() {
        Comparator<OfferingData> makeComponentSorter = new Comparator<OfferingData>() {
            @Override
            public int compare(OfferingData o1, OfferingData o2) {
                return o1.getComponent().compareTo(o2.getComponent());
            }
        };
        offeringDataList.sort(makeComponentSorter);
    }
}
