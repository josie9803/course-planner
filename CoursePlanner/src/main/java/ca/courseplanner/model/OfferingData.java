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

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
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
