package ca.courseplanner.model;

public class Course {
    private static long nextId = 1;
    private long courseId;
    private String catalogNumber;

    public Course(String catalogNumber) {
        this.courseId = nextId++;
        this.catalogNumber = catalogNumber;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }
}

