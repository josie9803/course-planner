package ca.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The course “number”. May be a number like 213, or a string like 105W or 1XX.
**/
public class Course {
    private String catalogNumber;
    private List<CourseOffering> courseOfferings;

    public Course(String catalogNumber) {
        this.catalogNumber = catalogNumber;
        this.courseOfferings = new ArrayList<>();
    }

    public void addCourseOffering(CourseOffering courseOffering) {
        courseOfferings.add(courseOffering);
    }

    public List<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public CourseOffering getCourseOfferingByIndex(int index) {
        return courseOfferings.get(index);
    }

    public CourseOffering getOfferingByKey(String key) {
        for (CourseOffering offering : courseOfferings) {
            if (offering.getOfferingKey().equals(key)) {
                return offering;
            }
        }
        return null;
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

