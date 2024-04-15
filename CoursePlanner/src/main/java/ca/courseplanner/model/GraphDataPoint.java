package ca.courseplanner.model;

public class GraphDataPoint {
    private final long semesterCode;
    private final long totalCoursesTaken;

    public GraphDataPoint(long semesterCode, long totalCoursesTaken) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }

    public long getSemesterCode() {
        return semesterCode;
    }

    public long getTotalCoursesTaken() {
        return totalCoursesTaken;
    }
}
