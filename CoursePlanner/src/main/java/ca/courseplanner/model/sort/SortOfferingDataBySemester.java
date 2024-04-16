package ca.courseplanner.model.sort;

import ca.courseplanner.model.OfferingData;

import java.util.Comparator;

/**
 * Class that use comparator to sort offering data by semester
 */

public class SortOfferingDataBySemester implements Comparator<OfferingData> {
    @Override
    public int compare(OfferingData o1, OfferingData o2) {
        return o1.getSemester().compareTo(o2.getSemester());
    }
}
