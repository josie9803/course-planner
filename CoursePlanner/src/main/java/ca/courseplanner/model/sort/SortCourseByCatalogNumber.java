package ca.courseplanner.model.sort;

import ca.courseplanner.model.Course;

import java.util.Comparator;

/**
 * Class that use comparator to course
 */

public class SortCourseByCatalogNumber implements Comparator<Course> {

    @Override
    public int compare(Course o1, Course o2) {
        return o1.getCatalogNumber().compareTo(o2.getCatalogNumber());
    }
}
