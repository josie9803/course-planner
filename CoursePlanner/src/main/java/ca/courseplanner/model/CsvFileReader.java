package ca.courseplanner.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReader {
    public static final int SEMESTER_INDEX = 0;
    public static final int SUBJECT_NAME_INDEX = 1;
    public static final int CATALOG_NUMBER_INDEX = 2;
    public static final int LOCATION_INDEX = 3;
    public static final int ENROLLMENT_CAP_INDEX = 4;
    public static final int ENROLLMENT_TOTAL_INDEX = 5;
    public static final int INSTRUCTOR_INDEX = 6;
    public static final int COMPONENT_INDEX = 7;

    List<OfferingData> offeringDataList;

    public CsvFileReader() {
        this.offeringDataList = new ArrayList<>();
    }

    public void readCSV(String filePath) {
        File csvCoursesFile = new File(filePath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvCoursesFile));
            reader.readLine();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split(",");
                OfferingData offeringData = new OfferingData.OfferingDataBuilder()
                        .setSemester(data[SEMESTER_INDEX])
                        .setSubjectName(data[SUBJECT_NAME_INDEX])
                        .setCatalogNumber(data[CATALOG_NUMBER_INDEX])
                        .setLocation(data[LOCATION_INDEX])
                        .setEnrollmentCap(Integer.parseInt(data[ENROLLMENT_CAP_INDEX]))
                        .setEnrollmentTotal(Integer.parseInt(data[ENROLLMENT_TOTAL_INDEX]))
                        .setInstructor(data[INSTRUCTOR_INDEX].trim())
                        .setComponent(data[COMPONENT_INDEX])
                        .build();
                offeringDataList.add(offeringData);
            }
        } catch (IOException e) {
            exitProgram();
        }
    }
    public void printToTerminal(){
        for (OfferingData offeringData : offeringDataList) {
            System.out.println(offeringData.getSemester() + " " +
                    offeringData.getSubjectName() + " " +
                    offeringData.getCatalogNumber() + " " +
                    offeringData.getLocation() + " " +
                    offeringData.getEnrollmentCap() + " " +
                    offeringData.getComponent() + " " +
                    offeringData.getEnrollmentTotal() + " " +
                    offeringData.getInstructor());
        }
    }

    private void exitProgram() {
        System.err.println("Error reading the file or its contents.");
        System.exit(1);
    }

}
