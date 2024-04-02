package ca.courseplanner.controllers;

import ca.courseplanner.AllApiDtoClasses.ApiAboutDTO;
import ca.courseplanner.model.CsvFileReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/api/about")
    public ApiAboutDTO getAboutInfo() {
        ApiAboutDTO aboutInfo = new ApiAboutDTO("Amazing course planner", "Nathan & Josie");
        return aboutInfo;
    }
    @GetMapping("/api/dump-model")
    public void dumpModel() {
        CsvFileReader csvFileReader = new CsvFileReader();
        csvFileReader.readCSV("data/course_data_2018.csv");
    }
}
