package ca.courseplanner.model;

import ca.courseplanner.controllers.AppController;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable class
 */
public class Department {
    private String name;
    private List<Course> courseList;

    public Department(String name) {
        this.name = name;
        this.courseList = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courseList.add(course);
    }

    public Course findCourse(String catalogNumber) {
        for (Course course : courseList) {
            if (course.getCatalogNumber().equals(catalogNumber)) {
                return course;
            }
        }
        return null;
    }
    public List<Course> getCourseList() {
        return courseList;
    }

    public Course getCourseById(int courseId) {
        Course course = null;
        if (courseId >= 0 && courseId < courseList.size()) {
            course = courseList.get(courseId);
        }
        return course;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
