package ca.courseplanner.model;

/**
 * The course “number”. May be a number like 213, or a string like 105W or 1XX.
**/
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

