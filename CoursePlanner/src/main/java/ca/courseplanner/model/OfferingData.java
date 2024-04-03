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

    public OfferingData() {
    }

    //    public static class OfferingDataBuilder {
//        private String semester;
//        private String subjectName;
//        private String catalogNumber;
//        private String location;
//        private int enrollmentCap;
//        private int enrollmentTotal;
//        private String instructor;
//        private String component;
//
//        public OfferingDataBuilder() {
//        }
//
//        public OfferingDataBuilder setSemester(String semester) {
//            this.semester = semester;
//            return this;
//        }
//
//        public OfferingDataBuilder setSubjectName(String subjectName) {
//            this.subjectName = subjectName;
//            return this;
//        }
//
//        public OfferingDataBuilder setCatalogNumber(String catalogNumber) {
//            this.catalogNumber = catalogNumber;
//            return this;
//        }
//
//        public OfferingDataBuilder setLocation(String location) {
//            this.location = location;
//            return this;
//        }
//
//        public OfferingDataBuilder setEnrollmentCap(int enrollmentCap) {
//            this.enrollmentCap = enrollmentCap;
//            return this;
//        }
//
//        public OfferingDataBuilder setEnrollmentTotal(int enrollmentTotal) {
//            this.enrollmentTotal = enrollmentTotal;
//            return this;
//        }
//
//        public OfferingDataBuilder setInstructor(String instructor) {
//            this.instructor = instructor;
//            return this;
//        }
//
//        public OfferingDataBuilder setComponent(String component) {
//            this.component = component;
//            return this;
//        }
//
//        public OfferingData build() {
//            return new OfferingData(this);
//        }
//    }
//
//    private OfferingData(OfferingDataBuilder builder) {
//        this.semester = builder.semester;
//        this.subjectName = builder.subjectName;
//        this.catalogNumber = builder.catalogNumber;
//        this.location = builder.location;
//        this.enrollmentCap = builder.enrollmentCap;
//        this.enrollmentTotal = builder.enrollmentTotal;
//        this.instructor = builder.instructor;
//        this.component = builder.component;
//    }

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
}
