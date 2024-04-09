package ca.courseplanner.model;

import java.util.*;

import static ca.courseplanner.model.CsvFileReader.*;

public class ProcessCsvData {
    private List<OfferingData> offeringDataList;

    public ProcessCsvData(List<String[]> data) {
        this.offeringDataList = new ArrayList<>();
        processCsvData(data);
    }

    public void processCsvData(List<String[]> data) {
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

    public List<OfferingData> getOfferingDataList() {
        return offeringDataList;
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
