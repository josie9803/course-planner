package ca.courseplanner.model;

import ca.courseplanner.model.observer.CourseObserver;

import java.util.ArrayList;
import java.util.List;

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
