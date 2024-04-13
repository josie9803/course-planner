package ca.courseplanner.model;

import ca.courseplanner.model.sort.*;

import java.util.*;

import static ca.courseplanner.model.filehandling.CsvFileReader.*;

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

            OfferingData existingOffering = findExistingOfferingData(subjectName, catalogNumber,
                    semester, location, component);
            if (existingOffering != null) {
                existingOffering.addEnrollmentCap(enrollmentCap);
                existingOffering.addEnrollmentTotal(enrollmentTotal);
                addInstructorsIfNotExisted(existingOffering, instructor);
            } else {
                OfferingData offeringData = new OfferingData(semester, subjectName,
                        catalogNumber, location,
                        enrollmentCap, component, enrollmentTotal, instructor);
                offeringDataList.add(offeringData);
            }
        }
        sortOfferingDataList();
    }
    private void sortOfferingDataList(){
        offeringDataList.sort(new SortOfferingDataByComponent());
        offeringDataList.sort(new SortOfferingDataByLocation());
        offeringDataList.sort(new SortOfferingDataBySemester());
        offeringDataList.sort(new SortOfferingDataByCourseName());
    }

    private void processDepartments() {
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

        Course course = department.findCourse(data.getCatalogNumber());
        if (course == null) {
            course = new Course(data.getCatalogNumber());
            department.addCourse(course);
            List<Course> courseList = department.getCourseList();
            courseList.sort(new SortCourseByCatalogNumber());
        }

        CourseOffering offering = course.findCourseOffering(data.getSemester(), data.getLocation());
        boolean isNewOffering = false;
        if (offering == null) {
            String offeringKey = data.getSemesterCode() + data.getLocation();
            offering = new CourseOffering(offeringKey, data.getLocation(),
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
        OfferingSection existingSection = offering.findSection(data.getComponent());
        if (existingSection != null) {
            int initialCap = existingSection.getEnrollmentCap();
            int initialTotal = existingSection.getEnrollmentTotal();

            existingSection.addEnrollmentCap(data.getEnrollmentCap());
            existingSection.addEnrollmentTotal(data.getEnrollmentTotal());

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

    private OfferingData findExistingOfferingData(String subjectName, String catalogNumber, String semester,
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
}
