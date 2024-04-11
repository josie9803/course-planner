package ca.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

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

    public List<Course> getCourseList() {
        return courseList;
    }

    public Course getCourseByIndex(int index) {
        return courseList.get(index);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
