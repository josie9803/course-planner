package ca.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private List<Course> courseList;

    public Department(String name){
        this.name = name;
        this.courseList = new ArrayList<>();
    }

    public void addCourse(Course course) {
        for (Course currentCourse : courseList) {
            if (currentCourse.getCatalogNumber().equals(course.getCatalogNumber())) {
                return;
            }
        }
        courseList.add(course);
    }

    public List<Course> getCourseList() {
        return courseList;
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
