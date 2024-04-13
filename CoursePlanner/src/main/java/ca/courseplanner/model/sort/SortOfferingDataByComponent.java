package ca.courseplanner.model.sort;

import ca.courseplanner.model.OfferingData;

import java.util.Comparator;

public class SortOfferingDataByComponent implements Comparator<OfferingData> {
    @Override
    public int compare(OfferingData o1, OfferingData o2) {
        return o1.getComponent().compareTo(o2.getComponent());
    }
}
