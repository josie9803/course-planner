package ca.courseplanner.AllApiDtoClasses;

import ca.courseplanner.model.OfferingSection;

/**
 * Data Transfer Object that represent an offering section
 */

public class ApiOfferingSectionDTO {
    public String type;
    public int enrollmentCap;
    public int enrollmentTotal;

    public static ApiOfferingSectionDTO makeFromOfferingSection(OfferingSection offeringSection) {
        ApiOfferingSectionDTO offeringSectionDTO = new ApiOfferingSectionDTO();
        offeringSectionDTO.type = offeringSection.getType();
        offeringSectionDTO.enrollmentCap = offeringSection.getEnrollmentCap();
        offeringSectionDTO.enrollmentTotal = offeringSection.getEnrollmentTotal();
        return offeringSectionDTO;
    }
}
