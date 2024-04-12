package ca.courseplanner.model.observer;

public interface CourseSubject {
    void addObserver(CourseObserver observer);
    void notifyObservers(String eventDescription);
}
