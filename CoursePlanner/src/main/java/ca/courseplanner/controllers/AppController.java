package ca.courseplanner.controllers;

import ca.courseplanner.AllApiDtoClasses.ApiAboutDTO;
import ca.courseplanner.model.CourseOffering;
import ca.courseplanner.model.CsvFileReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {
    @GetMapping("/about")
    public ApiAboutDTO getAboutInfo() {
        ApiAboutDTO aboutInfo = new ApiAboutDTO("Amazing course planner", "Nathan & Josie");
        return aboutInfo;
    }
    @GetMapping("/dump-model")
    public void dumpModel() {
        CsvFileReader csvFileReader = new CsvFileReader();
        csvFileReader.readCSV("data/small_course_data.csv");
       // csvFileReader.readCSV("data/small_data.csv");
//        csvFileReader.printToTerminal();

//        List<CourseOffering> courseOfferings = csvFileReader.readCSV("data/course_data_2018.csv");
//        csvFileReader.printToTerminal(courseOfferings);

        //csvFileReader.printOfferingDataToTerminal();
        csvFileReader.printCourseOfferingToTerminal();
    }
}
