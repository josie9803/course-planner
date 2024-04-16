package ca.courseplanner.AllApiDtoClasses;

import ca.courseplanner.model.Course;

/**
 * Data Transfer Object that represent a course
 */

public class ApiCourseDTO {
    public long courseId;
    public String catalogNumber;

    public static ApiCourseDTO makeFromCourse(Course course, int courseId) {
        ApiCourseDTO courseDTO = new ApiCourseDTO();
        courseDTO.courseId = courseId;
        courseDTO.catalogNumber = course.getCatalogNumber();
        return courseDTO;
    }
}
