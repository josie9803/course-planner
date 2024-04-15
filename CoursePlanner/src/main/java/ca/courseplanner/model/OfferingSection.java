package ca.courseplanner.model;

public class OfferingSection {
    private final String type;
    private int enrollmentCap;
    private int enrollmentTotal;

    public OfferingSection(String type, int enrollmentCap, int enrollmentTotal) {
        this.type = type;
        this.enrollmentCap = enrollmentCap;
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getType() {
        return type;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }
    public void addEnrollmentCap(int enrollmentCap){
        this.enrollmentCap += enrollmentCap;
    }
    public void addEnrollmentTotal(int enrollmentTotal){
        this.enrollmentTotal += enrollmentTotal;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    @Override
    public String toString() {
        return "OfferingSection{" +
                "type='" + type + '\'' +
                ", enrollmentCap=" + enrollmentCap +
                ", enrollmentTotal=" + enrollmentTotal +
                '}';
    }
}
