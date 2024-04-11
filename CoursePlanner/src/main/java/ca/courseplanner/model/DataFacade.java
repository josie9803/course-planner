package ca.courseplanner.model;

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

    private void processCoursesInDepartment() {
        for (OfferingData offeringData : offeringDataList) {
            String subjectName = offeringData.getSubjectName();
            String catalogNumber = offeringData.getCatalogNumber();

            Department department = findDepartmentBySubjectName(subjectName);
            if (department == null) {
                department = new Department(subjectName);
                departmentList.add(department);
            }

            Course course = department.findCourseByCatalogNumber(catalogNumber);
            if (course == null) {
                course = new Course(catalogNumber);
                department.addCourse(course);
            }

            String offeringKey = offeringData.getSemesterCode() + offeringData.getLocation();
            CourseOffering offering = course.getOfferingByKey(offeringKey);
            if (offering == null) {
                offering = new CourseOffering(offeringKey, offeringData.getLocation(),
                        offeringData.getInstructor(), offeringData.getTerm(),
                        offeringData.getSemesterCode(), offeringData.getYear());
                course.addCourseOffering(offering);
            }

            OfferingSection section = new OfferingSection(offeringData.getComponent(),
                    offeringData.getEnrollmentCap(), offeringData.getEnrollmentTotal());
            offering.addOfferingSection(section);
        }
    }

    private Department findDepartmentBySubjectName(String name) {
        for (Department department : departmentList) {
            if (department.getName().equals(name)) {
                return department;
            }
        }
        return null;
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
