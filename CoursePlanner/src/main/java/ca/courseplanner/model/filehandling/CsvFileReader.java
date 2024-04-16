package ca.courseplanner.model.filehandling;

import java.io.*;
import java.util.*;

/**
 * Class that read and parse the csv file in to list of string array
 */

public class CsvFileReader {
    public static final int SEMESTER_INDEX = 0;
    public static final int SUBJECT_NAME_INDEX = 1;
    public static final int CATALOG_NUMBER_INDEX = 2;
    public static final int LOCATION_INDEX = 3;
    public static final int ENROLLMENT_CAP_INDEX = 4;
    public static final int ENROLLMENT_TOTAL_INDEX = 5;
    public static final int INSTRUCTOR_INDEX = 6;
    public static final int COMPONENT_INDEX = 7;

    private List<String[]> rawData;

    public CsvFileReader() {
        this.rawData = new ArrayList<>();
    }

    public void readCSV(String filePath) {
        File csvCoursesFile = new File(filePath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvCoursesFile));
            reader.readLine();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] data = parseCSVLine(currentLine);
                rawData.add(data);
            }
            reader.close();
        } catch (IOException e) {
            exitProgram();
        }
    }

    private String[] parseCSVLine(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder();
        boolean insideQuotes = false;

        for (char character : line.toCharArray()) {
            if (character == '"') {
                insideQuotes = !insideQuotes;
            } else if (character == ',' && !insideQuotes) {
                parts.add(currentPart.toString());
                currentPart.setLength(0);
            } else {
                currentPart.append(character);
            }
        }
        parts.add(currentPart.toString());
        return parts.toArray(new String[0]);
    }

    public List<String[]> getRawData() {
        return rawData;
    }

    private void exitProgram() {
        System.err.println("Error reading the file or its contents.");
        System.exit(1);
    }
}
