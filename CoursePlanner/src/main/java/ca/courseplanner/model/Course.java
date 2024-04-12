package ca.courseplanner.model;

import ca.courseplanner.model.observer.CourseObserver;
import ca.courseplanner.model.observer.CourseSubject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The course “number”. May be a number like 213, or a string like 105W or 1XX.
 **/
public class Course implements CourseSubject {
    private String catalogNumber;
    private List<CourseOffering> courseOfferings;
    private List<CourseObserver> courseObservers;

    public Course(String catalogNumber) {
        this.catalogNumber = catalogNumber;
        this.courseOfferings = new ArrayList<>();
        this.courseObservers = new ArrayList<>();
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

    public String getCatalogNumber() {
        return catalogNumber;
    }

    @Override
    public String toString() {
        return "Course{" +
                "catalogNumber='" + catalogNumber + '\'' +
                '}';
    }

    @Override
    public void addObserver(CourseObserver observer) {
        courseObservers.add(observer);
    }

    @Override
    public void notifyObservers(String eventDescription) {
        for (CourseObserver observer : courseObservers) {
            observer.update(eventDescription);
        }
    }
}

