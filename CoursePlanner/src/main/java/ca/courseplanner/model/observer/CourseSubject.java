package ca.courseplanner.model.observer;

/**
 * Observer pattern: interface for subject (Course)
 */

public interface CourseSubject {
    void addObserver(CourseObserver observer);
    void notifyObservers(String eventDescription);
}
