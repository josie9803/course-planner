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
            String semester = rowData[SEMESTER_INDEX];
            String subjectName = rowData[SUBJECT_NAME_INDEX];
            String catalogNumber = rowData[CATALOG_NUMBER_INDEX];
            String location = rowData[LOCATION_INDEX];
            int enrollmentCap = Integer.parseInt(rowData[ENROLLMENT_CAP_INDEX]);
            int enrollmentTotal = Integer.parseInt(rowData[ENROLLMENT_TOTAL_INDEX]);
            String instructor = rowData[INSTRUCTOR_INDEX];
            String component = rowData[COMPONENT_INDEX];

            OfferingData existingOffering = findExistingOffering(subjectName, catalogNumber,
                    semester, location, component);
            if (existingOffering != null) {
                existingOffering.setEnrollmentCap(existingOffering.getEnrollmentCap() + enrollmentCap);
                existingOffering.setEnrollmentTotal(existingOffering.getEnrollmentTotal() + enrollmentTotal);
                addInstructorsIfExisted(existingOffering, instructor);
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
    }

    private void addInstructorsIfExisted(OfferingData existingOffering, String instructor) {
        if (!existingOffering.getInstructor().contains(instructor)) {
            String instructors = existingOffering.getInstructor();
            existingOffering.setInstructor(instructors + ", " + instructor);
        }
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
