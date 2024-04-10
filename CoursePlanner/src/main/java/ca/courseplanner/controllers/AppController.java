package ca.courseplanner.controllers;

import ca.courseplanner.AllApiDtoClasses.ApiAboutDTO;
import ca.courseplanner.AllApiDtoClasses.ApiCourseDTO;
import ca.courseplanner.AllApiDtoClasses.ApiCourseOfferingDTO;
import ca.courseplanner.AllApiDtoClasses.ApiDepartmentDTO;
import ca.courseplanner.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        DataFacade dataFacade = new DataFacade(listOfRawData);
        offeringDataList = dataFacade.getOfferingDataList();
        departmentList = dataFacade.getDepartmentList();
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

    @GetMapping("/departments/{id}/courses")
    public List<ApiCourseDTO> getCourses(@PathVariable("id") int departmentId) {
        Department department = departmentList.get(departmentId);
        List<ApiCourseDTO> courseDTO = new ArrayList<>();
        int courseId = 0;
        for (Course course : department.getCourseList()) {
            ApiCourseDTO dto = ApiCourseDTO.makeFromCourse(course, courseId++);
            courseDTO.add(dto);
        }
        return courseDTO;
    }

    @GetMapping("departments/{deptId}/courses/{courseId}/offerings")
    public List<ApiCourseOfferingDTO> getCourseOfferings(@PathVariable("deptId") int departmentId,
                                                         @PathVariable("courseId") int courseId) {
        Department department = departmentList.get(departmentId);
        Course course = department.getCourseByIndex(courseId);
        List<ApiCourseOfferingDTO> courseOfferingDTO = new ArrayList<>();
        int courseOfferingId = 0;
        for (CourseOffering courseOffering : course.getCourseOfferings()) {
            ApiCourseOfferingDTO dto = ApiCourseOfferingDTO.makeFromCourseOffering(courseOffering, courseOfferingId++);
            courseOfferingDTO.add(dto);
        }
        return courseOfferingDTO;
    }
}
