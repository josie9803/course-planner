package ca.courseplanner.controllers;

import ca.courseplanner.AllApiDtoClasses.ApiAboutDTO;
import ca.courseplanner.AllApiDtoClasses.ApiDepartmentDTO;
import ca.courseplanner.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {
    private List<OfferingData> offeringDataList = new ArrayList<>();
    private List<Department> departmentList = new ArrayList<>();

    @PostConstruct
    public void initData() {
        CsvFileReader csvFileReader = new CsvFileReader();
        csvFileReader.readCSV("data/course_data_2018.csv");
        List<String[]> listOfRawData = csvFileReader.getRawData();

        ProcessCsvData processCsvData = new ProcessCsvData(listOfRawData);
        offeringDataList = processCsvData.getOfferingDataList();
        departmentList = processCsvData.getUniqueDepartments();
    }

    @GetMapping("/about")
    public ApiAboutDTO getAboutInfo() {
        return new ApiAboutDTO("Amazing course planner", "Nathan & Josie");
    }
    @GetMapping("/dump-model")
    public void dumpModel() {
        Printer.printToTerminal(offeringDataList);
    }

    @GetMapping("/departments")
    public List<ApiDepartmentDTO> getDepartments() {
        List<ApiDepartmentDTO> departmentDTO = new ArrayList<>();
        for (int i = 0; i < departmentList.size(); i++) {
            Department department = departmentList.get(i);
            ApiDepartmentDTO dto = ApiDepartmentDTO.makeFromDepartment(department, i);
            departmentDTO.add(dto);
        }
        return departmentDTO;
    }
}
