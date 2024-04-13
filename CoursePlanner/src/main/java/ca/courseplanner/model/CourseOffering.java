package ca.courseplanner.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Immutable class
 **/

public class CourseOffering {
    private String offeringKey;
    private String location;
    private String instructors;
    private String term;
    private long semesterCode;
    private int year;
    private List<OfferingSection> offeringSections;

    public CourseOffering(String offeringKey, String location, String instructors, String term,
                          long semesterCode, int year) {
        this.offeringKey = offeringKey;
        this.location = location;
        this.instructors = instructors;
        this.term = term;
        this.semesterCode = semesterCode;
        this.year = year;
        this.offeringSections = new ArrayList<>();
    }
    public String getOfferingKey() {
        return offeringKey;
    }

    public void addOfferingSection(OfferingSection offeringSection) {
        offeringSections.add(offeringSection);
    }

    public List<OfferingSection> getOfferingSections() {
        return offeringSections;
    }
    public OfferingSection findSection(String componentType) {
        for (OfferingSection section : offeringSections) {
            if (section.getType().equals(componentType)) {
                return section;
            }
        }
        return null;
    }

    public String getLocation() {
        return location;
    }

    public String getInstructors() {
        return instructors;
    }

    public String getTerm() {
        return term;
    }

    public long getSemesterCode() {
        return semesterCode;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "CourseOffering{" +
                "location='" + location + '\'' +
                ", instructors='" + instructors + '\'' +
                ", term='" + term + '\'' +
                ", semesterCode=" + semesterCode +
                ", year=" + year +
                ", offeringSections=" + offeringSections +
                '}';
    }
}
