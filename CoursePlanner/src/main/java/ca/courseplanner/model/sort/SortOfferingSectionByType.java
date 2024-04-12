package ca.courseplanner.model.sort;

import ca.courseplanner.model.OfferingSection;

import java.util.Comparator;

public class SortOfferingSectionByType implements Comparator<OfferingSection> {

    @Override
    public int compare(OfferingSection o1, OfferingSection o2) {
        return o1.getType().compareTo(o2.getType());
    }
}
