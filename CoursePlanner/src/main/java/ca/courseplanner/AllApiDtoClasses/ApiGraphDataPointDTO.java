package ca.courseplanner.AllApiDtoClasses;

import ca.courseplanner.model.GraphDataPoint;

/**
 * Data Transfer Object that represent a data point for courses taken in semester
 */

public class ApiGraphDataPointDTO {
    public long semesterCode;
    public long totalCoursesTaken;
    public static ApiGraphDataPointDTO makeFromGraphDataPoint(GraphDataPoint dataPoint){
        ApiGraphDataPointDTO graph = new ApiGraphDataPointDTO();
        graph.semesterCode = dataPoint.getSemesterCode();
        graph.totalCoursesTaken = dataPoint.getTotalCoursesTaken();
        return graph;
    }
}
