package ca.courseplanner.AllApiDtoClasses;

import ca.courseplanner.model.OfferingData;

public class ApiOfferingDataDTO {
    public String semester;
    public String subjectName;
    public String catalogNumber;
    public String location;
    public int enrollmentCap;
    public String component;
    public int enrollmentTotal;
    public String instructor;

    public static ApiOfferingDataDTO makeFromOfferingData(OfferingData data) {
        ApiOfferingDataDTO dataDTO = new ApiOfferingDataDTO();
        dataDTO.semester = data.getSemester();
        dataDTO.subjectName = data.getSubjectName();
        dataDTO.catalogNumber = data.getCatalogNumber();
        dataDTO.location = data.getLocation();
        dataDTO.enrollmentCap = data.getEnrollmentCap();
        dataDTO.component = data.getComponent();
        dataDTO.enrollmentTotal = data.getEnrollmentTotal();
        dataDTO.instructor = data.getInstructor();
        return dataDTO;
    }

    public static OfferingData toOfferingData(ApiOfferingDataDTO offering) {
        return new OfferingData(offering.semester, offering.subjectName,
                offering.catalogNumber, offering.location,
                offering.enrollmentCap, offering.component,
                offering.enrollmentTotal, offering.instructor);
    }

    public String getComponent() {
        return component;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }
}
