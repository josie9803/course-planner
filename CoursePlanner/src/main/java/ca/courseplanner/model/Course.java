package ca.courseplanner.model;

import ca.courseplanner.model.observer.CourseObserver;
import ca.courseplanner.model.observer.CourseSubject;
import ca.courseplanner.model.sort.SortCourseOfferingBySemesterCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable class
 **/
public class Course implements CourseSubject {
    private final String catalogNumber;
    private final List<CourseOffering> courseOfferings;
    private final List<CourseObserver> courseObservers;

    public Course(String catalogNumber) {
        this.catalogNumber = catalogNumber;
        this.courseOfferings = new ArrayList<>();
        this.courseObservers = new ArrayList<>();
    }

    public void addCourseOffering(CourseOffering courseOffering) {
        courseOfferings.add(courseOffering);
    }
    public void sortCourse(){
        courseOfferings.sort(new SortCourseOfferingBySemesterCode());
    }
    public CourseOffering findCourseOffering(String semester, String location) {
        String offeringKey = semester + location;
        for (CourseOffering offering : courseOfferings) {
            if (offering.getOfferingKey().equals(offeringKey)) {
                return offering;
            }
        }
        return null;
    }

    public List<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public CourseOffering getCourseOfferingById(int courseOfferingId) {
        CourseOffering courseOffering = null;
        if (courseOfferingId >= 0 && courseOfferingId < courseOfferings.size()) {
            courseOffering = courseOfferings.get(courseOfferingId);
        }
        return courseOffering;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }
    public long getTotalEnrollmentInOneSemester(long semesterCode){
        long totalStudents = 0;
        for (CourseOffering courseOffering : courseOfferings){
            if (courseOffering.getSemesterCode() == semesterCode){
                totalStudents += courseOffering.getTotalEnrollmentInLecture();
            }
        }
        return totalStudents;
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

