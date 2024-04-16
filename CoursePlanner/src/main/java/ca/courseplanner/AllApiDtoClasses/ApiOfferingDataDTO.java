package ca.courseplanner.AllApiDtoClasses;

import ca.courseplanner.model.OfferingData;

/**
 * Data Transfer Object that represent an offering data
 */

public class ApiOfferingDataDTO {
    public String semester;
    public String subjectName;
    public String catalogNumber;
    public String location;
    public int enrollmentCap;
    public String component;
    public int enrollmentTotal;
    public String instructor;

    public static OfferingData toOfferingData(ApiOfferingDataDTO offering) {
        return new OfferingData(offering.semester, offering.subjectName,
                offering.catalogNumber, offering.location,
                offering.enrollmentCap, offering.component,
                offering.enrollmentTotal, offering.instructor);
    }
}
