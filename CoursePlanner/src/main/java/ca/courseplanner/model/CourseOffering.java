package ca.courseplanner.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Same as OfferingData, except:
 * has Course: in place for "catalog number"
 * has multiple OfferingSection: in place for "enrollmentCap, enrollmentTotal, componentCode"
 * has Department: in place for "subject"
 * has "year, term" extracted from "OfferingData.semester"
 * <p>
 * semesterCode is semester, location is location, instructor is instructor
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstructors() {
        return instructors;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public long getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(long semesterCode) {
        this.semesterCode = semesterCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
