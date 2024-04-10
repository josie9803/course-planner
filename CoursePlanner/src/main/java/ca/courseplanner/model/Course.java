package ca.courseplanner.model;

/**
 * The course “number”. May be a number like 213, or a string like 105W or 1XX.
**/
public class Course {
    private String catalogNumber;

    public Course(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    @Override
    public String toString() {
        return "Course{" +
                "catalogNumber='" + catalogNumber + '\'' +
                '}';
    }
}

