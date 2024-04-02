package ca.courseplanner.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReader {
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
                OfferingData offeringData = new OfferingData(
                        data[0], data[1], data[2], data[3],
                        Integer.parseInt(data[4]), data[7],
                        Integer.parseInt(data[5]), data[6].trim());
                offeringDataList.add(offeringData);
            }
        } catch (IOException e) {
            exitProgram();
        }

        printToTerminal();
    }
    private void printToTerminal(){
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
