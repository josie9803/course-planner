package ca.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Same as OfferingData, except:
 * has Course: in place for "catalog number"
 * has multiple OfferingSection: in place for "enrollmentCap, enrollmentTotal, componentCode"
 * has Department: in place for "subject"
 * has "year, term" extracted from "OfferingData.semester"
 *
 * semesterCode is semester, location is location, instructor is instructor
 **/

public class CourseOffering {
    private long courseOfferingId;
    private String location;
    private String instructors;
    private String term;
    private long semesterCode;
    private int year;
    private String courseName;
    private List<OfferingSection> sections;


    public CourseOffering() {
        this.sections = new ArrayList<>();
    }

    public CourseOffering createCourseOfferingFromOfferingData(OfferingData offeringData){
        this.location = offeringData.getLocation();
        this.instructors = offeringData.getInstructor();
        this.semesterCode = Long.parseLong(offeringData.getSemester());
        return this;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<OfferingSection> getSections() {
        return sections;
    }

    public void addSections(OfferingSection section) {
        sections.add(section);
    }


    public void setSections(List<OfferingSection> sections) {
        this.sections = sections;
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
                "courseOfferingId=" + courseOfferingId +
                ", location='" + location + '\'' +
                ", instructors='" + instructors + '\'' +
                ", term='" + term + '\'' +
                ", semesterCode=" + semesterCode +
                ", year=" + year +
                ", courseName='" + courseName + '\'' +
                ", sections=" + sections +
                '}';
    }
}
