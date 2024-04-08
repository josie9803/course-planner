package ca.courseplanner.model;

import java.io.*;
import java.util.*;

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
                String[] readData = new String[8];
                readData[0] = data[0];
                readData[1] = data[1];
                readData[2] = data[2];
                readData[3] = data[3];
                readData[4] = data[4];
                readData[5] = data[5];
                readData[6] = data[6];
                readData[7] = data[7];
                rawData.add(readData);
                System.out.println(rawData.size());
            }
            reader.close();
        } catch (IOException e) {
            exitProgram();
        }
    }

    public List<String[]> getRawData() {
        return rawData;
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

    //    public void printToTerminal() {
//        Map<String, Map<String, Map<String, Map<String, OfferingData>>>> courseOfferings = new HashMap<>();
//
//        // Group offering data by course, semester, location, and component
//        for (OfferingData offeringData : offeringDataList) {
//            String key = offeringData.getSubjectName() + " " + offeringData.getCatalogNumber();
//            courseOfferings.putIfAbsent(key, new LinkedHashMap<>());
//
//            String semester = offeringData.getSemester();
//            courseOfferings.get(key).putIfAbsent(semester, new LinkedHashMap<>());
//
//            String location = offeringData.getLocation();
//            courseOfferings.get(key).get(semester).putIfAbsent(location, new LinkedHashMap<>());
//
//            String type = offeringData.getComponent();
//            if (!courseOfferings.get(key).get(semester).get(location).containsKey(type)) {
//                courseOfferings.get(key).get(semester).get(location).put(type, offeringData);
//            } else {
//                OfferingData existing = courseOfferings.get(key).get(semester).get(location).get(type);
//                existing.setEnrollmentTotal(existing.getEnrollmentTotal() + offeringData.getEnrollmentTotal());
//                existing.setEnrollmentCap(existing.getEnrollmentCap() + offeringData.getEnrollmentCap());
//            }
//        }
//
//        // Print course offerings
//        for (Map.Entry<String, Map<String, Map<String, Map<String, OfferingData>>>> entry : courseOfferings.entrySet()) {
//            String course = entry.getKey();
//            Map<String, Map<String, Map<String, OfferingData>>> semesters = entry.getValue();
//
//            System.out.println(course);
//
//            for (Map.Entry<String, Map<String, Map<String, OfferingData>>> semesterEntry : semesters.entrySet()) {
//                String semester = semesterEntry.getKey();
//                Map<String, Map<String, OfferingData>> locations = semesterEntry.getValue();
//
//                for (Map.Entry<String, Map<String, OfferingData>> locationEntry : locations.entrySet()) {
//                    String location = locationEntry.getKey();
//                    Map<String, OfferingData> types = locationEntry.getValue();
//
//                    Set<String> instructors = new HashSet<>();
//                    for (OfferingData offering : types.values()) {
//                        String instructorName = offering.getInstructor();
//                        if (!instructorName.equals("(null)") && !instructorName.equals("<null>")) {
//                            ;
//                            instructors.add(instructorName.trim());
//                        }
//                    }
//
//                    String instructor = String.join(", ", instructors);
//
//                    System.out.println("\t " + semester + " in " + location + " by " + instructor);
//
//                    // Print offerings for each type
//                    for (OfferingData offering : types.values()) {
//                        System.out.println("\t\t   Type=" + offering.getComponent() + ", Enrollment=" + offering.getEnrollmentTotal() + "/" + offering.getEnrollmentCap());
//                    }
//                }
//            }
//        }
//    }

    private void exitProgram() {
        System.err.println("Error reading the file or its contents.");
        System.exit(1);
    }
}
