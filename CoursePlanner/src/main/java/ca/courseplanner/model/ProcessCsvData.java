package ca.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

public class ProcessCsvData {
    private List<OfferingData> offeringDataList;

    public ProcessCsvData(List<String[]> data) {
        this.offeringDataList = new ArrayList<>();
        processCsvData(data);
    }

    public void processCsvData(List<String[]> data){
    }

    public List<OfferingData> getOfferingDataList() {
        return offeringDataList;
    }
}
