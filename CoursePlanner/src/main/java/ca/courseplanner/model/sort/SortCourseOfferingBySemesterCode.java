package ca.courseplanner.model.sort;

import ca.courseplanner.model.CourseOffering;

import java.util.Comparator;

public class SortCourseOfferingBySemesterCode implements Comparator<CourseOffering> {

    @Override
    public int compare(CourseOffering o1, CourseOffering o2) {
        return Long.compare(o1.getSemesterCode(), o2.getSemesterCode());
    }
}
