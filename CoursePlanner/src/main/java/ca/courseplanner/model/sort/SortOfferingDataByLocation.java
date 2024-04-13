package ca.courseplanner.model.sort;

import ca.courseplanner.model.OfferingData;

import java.util.Comparator;

public class SortOfferingDataByLocation implements Comparator<OfferingData> {
    @Override
    public int compare(OfferingData o1, OfferingData o2) {
        return o1.getLocation().compareTo(o2.getLocation());
    }
}
