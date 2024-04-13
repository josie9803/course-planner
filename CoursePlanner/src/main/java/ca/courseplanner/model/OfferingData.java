package ca.courseplanner.model;

public class OfferingData {
    private String semester;
    private String location;
    private String instructor;
    private String subjectName;
    private String catalogNumber;
    private int enrollmentCap;
    private int enrollmentTotal;
    private String component;

    public OfferingData(String semester, String subjectName,
                        String catalogNumber, String location,
                        int enrollmentCap, String component,
                        int enrollmentTotal, String instructor) {
        this.semester = semester;
        this.subjectName = subjectName;
        this.catalogNumber = catalogNumber;
        this.location = location;
        this.enrollmentCap = enrollmentCap;
        this.component = component;
        this.enrollmentTotal = enrollmentTotal;
        this.instructor = instructor;
    }

    public String getSemester() {
        return semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public String getLocation() {
        return location;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public String getComponent() {
        return component;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getCourseName() {
        return subjectName + " " + catalogNumber;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public void addEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap += enrollmentCap;
    }
    public void addEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal += enrollmentTotal;
    }

    public String getTerm() {
        char termCode = semester.charAt(semester.length() - 1);
        return switch (termCode) {
            case '1' -> "Spring";
            case '4' -> "Summer";
            case '7' -> "Fall";
            default -> "Unknown";
        };
    }

    public long getSemesterCode() {
        return Long.parseLong(semester);
    }

    public int getYear() {
        if (semester.length() == 4) {
            int X = Integer.parseInt(semester.substring(0, 1));
            int Y = Integer.parseInt(semester.substring(1, 2));
            int Z = Integer.parseInt(semester.substring(2, 3));
            return 1900 + 100 * X + 10 * Y + Z;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "OfferingData{" +
                "semester='" + semester + '\'' +
                ", location='" + location + '\'' +
                ", instructor='" + instructor + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", catalogNumber='" + catalogNumber + '\'' +
                ", enrollmentCap=" + enrollmentCap +
                ", enrollmentTotal=" + enrollmentTotal +
                ", component='" + component + '\'' +
                '}';
    }
}
