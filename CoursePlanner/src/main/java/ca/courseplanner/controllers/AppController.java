package ca.courseplanner.controllers;

import ca.courseplanner.AllApiDtoClasses.ApiAboutDTO;
import ca.courseplanner.model.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {
    @GetMapping("/about")
    public ApiAboutDTO getAboutInfo() {
        return new ApiAboutDTO("Amazing course planner", "Nathan & Josie");
    }
    @GetMapping("/dump-model")
    public void dumpModel() {
        CsvFileReader csvFileReader = new CsvFileReader();
        csvFileReader.readCSV("data/course_data_2018.csv");
        List<String[]> listOfRawData = csvFileReader.getRawData();

        ProcessCsvData processCsvData = new ProcessCsvData(listOfRawData);

        List<OfferingData> newList = processCsvData.getOfferingDataList();
        Printer.printToTerminal(newList);
    }
}
