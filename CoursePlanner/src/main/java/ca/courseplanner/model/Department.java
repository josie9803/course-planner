package ca.courseplanner.model;

import ca.courseplanner.model.sort.SortCourseByCatalogNumber;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Immutable class that represent one department, it maintains a list of course and semester
 */

public class Department {
    private final String name;
    private final List<Course> courseList;
    private final List<Long> semesterCodeList;

    public Department(String name) {
        this.name = name;
        this.courseList = new ArrayList<>();
        this.semesterCodeList = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courseList.add(course);
    }

    public void sortCourse(){
        courseList.sort(new SortCourseByCatalogNumber());
    }
    public Course findCourse(String catalogNumber) {
        for (Course course : courseList) {
            if (course.getCatalogNumber().equals(catalogNumber)) {
                return course;
            }
        }
        return null;
    }

    public void addSemesterCodeIfNotExisted(long semesterCode){
        if (!semesterCodeList.contains(semesterCode)) {
            semesterCodeList.add(semesterCode);
        }
        semesterCodeList.sort(Comparator.naturalOrder());
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

    private long getTotalEnrollmentInOneSemester(long semesterCode){
        long totalStudents = 0;
        for (Course course : courseList){
            totalStudents += course.getTotalEnrollmentInOneSemester(semesterCode);
        }
        return totalStudents;
    }
    public List<GraphDataPoint> createListOfDataPoint(){
        List<GraphDataPoint> graphDataPoints = new ArrayList<>();
        long totalStudents = 0;
        for (Long semesterCode : semesterCodeList){
            totalStudents += getTotalEnrollmentInOneSemester(semesterCode);
            graphDataPoints.add(new GraphDataPoint(semesterCode, totalStudents));
            totalStudents = 0;
        }
        return graphDataPoints;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
