package ca.courseplanner.model.observer;

/**
 * Observer pattern: interface for Observer (Watcher)
 */

public interface CourseObserver {
    void update(String eventDescription);
}
