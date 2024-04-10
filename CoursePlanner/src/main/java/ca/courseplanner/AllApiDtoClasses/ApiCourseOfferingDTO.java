package ca.courseplanner.AllApiDtoClasses;

import ca.courseplanner.model.CourseOffering;

public class ApiCourseOfferingDTO {
    public long courseOfferingId;
    public String location;
    public String instructors;
    public String term;
    public long semesterCode;
    public int year;

    public static ApiCourseOfferingDTO makeFromCourseOffering(CourseOffering courseOffering, int courseOfferingId) {
        ApiCourseOfferingDTO courseOfferingDTO = new ApiCourseOfferingDTO();
        courseOfferingDTO.courseOfferingId = courseOfferingId;
        courseOfferingDTO.location = courseOffering.getLocation();
        courseOfferingDTO.instructors = courseOffering.getInstructors();
        courseOfferingDTO.term = courseOffering.getTerm();
        courseOfferingDTO.semesterCode = courseOffering.getSemesterCode();
        courseOfferingDTO.year = courseOffering.getYear();
        return courseOfferingDTO;
    }
}
