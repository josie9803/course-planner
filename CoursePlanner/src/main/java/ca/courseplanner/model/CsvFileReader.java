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

    private List<OfferingData> rawOfferingData;
    private List<OfferingData> processedOfferingData;
    private List<CourseOffering> courseOfferingsList;

    public CsvFileReader() {
        this.rawOfferingData = new ArrayList<>();
        this.courseOfferingsList = new ArrayList<>();
    }

    public void readCSV(String filePath) {
        List<CourseOffering> courseOfferings = new ArrayList<>();
        File csvCoursesFile = new File(filePath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvCoursesFile));
            reader.readLine();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] data = parseCSVLine(currentLine);
                OfferingData offeringData = new OfferingData(
                        data[SEMESTER_INDEX], data[SUBJECT_NAME_INDEX].trim(),
                        data[CATALOG_NUMBER_INDEX], data[LOCATION_INDEX].trim(),
                        Integer.parseInt(data[ENROLLMENT_CAP_INDEX]), data[COMPONENT_INDEX],
                        Integer.parseInt(data[ENROLLMENT_TOTAL_INDEX]), data[INSTRUCTOR_INDEX].trim());

//                CourseOffering courseOffering = findOrCreateCourseOffering(courseOfferings, offeringData);
//                OfferingSection section = createOfferingSection(offeringData);
//                courseOffering.addSections(section);
                rawOfferingData.add(offeringData);
                addToProcessedOfferingDataList(offeringData);
            }
            reader.close();
        } catch (IOException e) {
            exitProgram();
        }
//        return courseOfferings;
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

    private void addToProcessedOfferingDataList(OfferingData offeringData){
        //check if the courseFromOfferingData exists?
        //1.check courseName first
        int alreadyExisted = 0;
        String courseName = offeringData.getSubjectName() + offeringData.getCatalogNumber();
//        for (CourseOffering courseOffering : courseOfferingsList){
//            if (courseOffering.getCourseName().equals(courseName)
//            && courseOffering.getLocation().equals(offeringData.getLocation())
//            && courseOffering.getSemesterCode() == Long.parseLong(offeringData.getSemester()))
//            {
//                alreadyExisted = 1;
//            }
//            else {
//                //
//            }
//            if (alreadyExisted == 0){
//                CourseOffering newCourseOffering = new CourseOffering();
//                newCourseOffering.setCourseName(courseName);
//                newCourseOffering.setLocation(offeringData.getLocation());
//                newCourseOffering.setInstructors(offeringData.getInstructor());
//                newCourseOffering.setSemesterCode(Long.parseLong(offeringData.getSemester()));
//                courseOfferingsList.add(newCourseOffering);
//            }
//        }
        CourseOffering newCourseOffering = new CourseOffering();
        newCourseOffering.setCourseName(courseName);
        newCourseOffering.setLocation(offeringData.getLocation());
        newCourseOffering.setInstructors(offeringData.getInstructor());
        newCourseOffering.setSemesterCode(Long.parseLong(offeringData.getSemester()));
        courseOfferingsList.add(newCourseOffering);
    }

    public void printCourseOfferingToTerminal(){
        System.out.println(courseOfferingsList.size());
//        for (CourseOffering courseOffering : courseOfferingsList) {
//            System.out.println(courseOffering.toString());
//            System.out.println("////////////////");
//        }
    }


//    private CourseOffering findOrCreateCourseOffering(List<CourseOffering> courseOfferings, OfferingData offeringData) {
//        for (CourseOffering courseOffering : courseOfferings) {
//            if (courseOffering.getLocation().equals(offeringData.getLocation())) {
//                return courseOffering;
//            }
//        }
//
//        // create a new one newCourseOffering
//        CourseOffering newCourseOffering = new CourseOffering();
//        newCourseOffering.setLocation(offeringData.getLocation());
//        newCourseOffering.setInstructors(offeringData.getInstructor());
//        newCourseOffering.setSemesterCode(Long.parseLong(offeringData.getSemester()));
//
//        // Create a new Course
//        Course course = new Course(offeringData.getCatalogNumber());
//        newCourseOffering.setCourse(course);
//        courseOfferings.add(newCourseOffering);
//
//        return newCourseOffering;
//    }

    private OfferingSection createOfferingSection(OfferingData offeringData) {
        String type = offeringData.getComponent();
        int enrollmentCap = offeringData.getEnrollmentCap();
        int enrollmentTotal = offeringData.getEnrollmentTotal();
        return new OfferingSection(type, enrollmentCap, enrollmentTotal);
    }

    public void printOfferingDataToTerminal(){
        System.out.println("hello");
        for (OfferingData offeringData : rawOfferingData) {
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


//    public void printToTerminal(List<CourseOffering> courseOfferings) {
//        // Group offerings by course number
//        Map<String, List<CourseOffering>> offeringsByCourse = groupOfferingsByCourse(courseOfferings);
//
//        // Print course offerings
//        for (Map.Entry<String, List<CourseOffering>> entry : offeringsByCourse.entrySet()) {
//            String courseNumber = entry.getKey();
//            List<CourseOffering> offerings = entry.getValue();
//
//            System.out.println(courseNumber);
//            for (CourseOffering offering : offerings) {
//                printOffering(offering);
//            }
//        }
//    }

//    private Map<String, List<CourseOffering>> groupOfferingsByCourse(List<CourseOffering> courseOfferings) {
//        Map<String, List<CourseOffering>> offeringsByCourse = new HashMap<>();
//        for (CourseOffering offering : courseOfferings) {
//            String courseNumber = offering.getCourse().getCatalogNumber();
//            offeringsByCourse.computeIfAbsent(courseNumber, k -> new ArrayList<>()).add(offering);
//        }
//        return offeringsByCourse;
//    }

    private void printOffering(CourseOffering offering) {
        String semesterLocationKey = offering.getSemesterCode() + " in " + offering.getLocation();
        System.out.println(semesterLocationKey + " by " + offering.getInstructors());

        // Group offering sections by type
        Map<String, OfferingSection> sectionsByType = groupSectionsByType(offering.getSections());

        // Print offering sections
        for (Map.Entry<String, OfferingSection> entry : sectionsByType.entrySet()) {
            String type = entry.getKey();
            OfferingSection section = entry.getValue();
            System.out.println("\tType=" + type + ", Enrollment=" + section.getEnrollmentTotal() + "/" + section.getEnrollmentCap());
        }
    }

    private Map<String, OfferingSection> groupSectionsByType(List<OfferingSection> sections) {
        Map<String, OfferingSection> sectionsByType = new HashMap<>();
        for (OfferingSection section : sections) {
            if (!sectionsByType.containsKey(section.getType())) {
                sectionsByType.put(section.getType(), section);
            } else {
                OfferingSection existingSection = sectionsByType.get(section.getType());
                existingSection.setEnrollmentTotal(existingSection.getEnrollmentTotal() + section.getEnrollmentTotal());
                existingSection.setEnrollmentCap(existingSection.getEnrollmentCap() + section.getEnrollmentCap());
            }
        }
        return sectionsByType;
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
