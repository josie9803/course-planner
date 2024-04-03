package ca.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

public class CourseOffering {
    private long courseOfferingId;
    private String location;
    private String instructors;
    private String term;
    private long semesterCode;
    private int year;
    private Course course;
    private List<OfferingSection> sections;


    public CourseOffering() {
        this.course = null;
        this.sections = new ArrayList<>();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
                "location='" + location + '\'' +
                ", instructors='" + instructors + '\'' +
                ", term='" + term + '\'' +
                ", semesterCode=" + semesterCode +
                ", year=" + year +
                ", sections=" + sections +
                '}';
    }
}
