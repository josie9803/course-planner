package ca.courseplanner.model.watcher;

import ca.courseplanner.model.Course;
import ca.courseplanner.model.Department;
import ca.courseplanner.model.observer.CourseObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete watcher class that use observer pattern and implements the observer interface
 */

public class Watcher implements CourseObserver {
    private Department department;
    private Course course;
    private List<String> events;

    public Watcher(Department department, Course course) {
        this.department = department;
        this.course = course;
        this.events = new ArrayList<>();
        course.addObserver(this);
    }

    @Override
    public void update(String eventDescription) {
        events.add(eventDescription);
    }

    public Department getDepartment() {
        return department;
    }

    public Course getCourse() {
        return course;
    }

    public List<String> getEvents() {
        return events;
    }
}
