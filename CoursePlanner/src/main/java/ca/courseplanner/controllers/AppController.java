package ca.courseplanner.controllers;

import ca.courseplanner.AllApiDtoClasses.*;
import ca.courseplanner.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {
    private List<OfferingData> offeringDataList = new ArrayList<>();
    private List<Department> departmentList = new ArrayList<>();
    DataFacade dataFacade;
    WatcherManager manager = new WatcherManager();

    @PostConstruct
    public void initData() {
        CsvFileReader csvFileReader = new CsvFileReader();
        csvFileReader.readCSV("data/course_data_2018.csv");
        List<String[]> listOfRawData = csvFileReader.getRawData();

        dataFacade = new DataFacade(listOfRawData);
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

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings")
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

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings/{offeringId}")
    public List<ApiOfferingSectionDTO> getOfferingSections(@PathVariable("deptId") int departmentId,
                                                           @PathVariable("courseId") int courseId,
                                                           @PathVariable("offeringId") int offeringId) {
        Department department = departmentList.get(departmentId);
        Course course = department.getCourseByIndex(courseId);
        CourseOffering offering = course.getCourseOfferingByIndex(offeringId);
        List<ApiOfferingSectionDTO> offeringSectionDTO = new ArrayList<>();
        for (OfferingSection section : offering.getOfferingSections()) {
            ApiOfferingSectionDTO dto = ApiOfferingSectionDTO.makeFromOfferingSection(section);
            offeringSectionDTO.add(dto);
        }
        return offeringSectionDTO;
    }

    @PostMapping("/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiOfferingSectionDTO addOffering(@RequestBody ApiOfferingDataDTO newOfferingData) {
        OfferingData offeringData = ApiOfferingDataDTO.toOfferingData(newOfferingData);
        dataFacade.processOfferingData(offeringData);

        Department department = dataFacade.findDepartment(offeringData.getSubjectName());
        Course course = dataFacade.findCourse(department, offeringData.getCatalogNumber());
        CourseOffering offering = dataFacade.findOffering(course, offeringData);
        OfferingSection updatedSection = dataFacade.findSection(offering, offeringData.getComponent());

        return ApiOfferingSectionDTO.makeFromOfferingSection(updatedSection);
    }

    @GetMapping("/watchers")
    public List<ApiWatcherDTO> getWatchers() {
        List<Watcher> watchers = manager.getWatchers();
        List<ApiWatcherDTO> watcherDTO = new ArrayList<>();
        int watcherId = 1;
        for (Watcher watcher : watchers) {
            watcherDTO.add(ApiWatcherDTO.makeFromWatcher(watcher, departmentList, watcherId++));
        }
        return watcherDTO;
    }

    @PostMapping("/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiWatcherDTO createWatcher(@RequestBody ApiWatcherCreateDTO watcherCreateDTO) {
        WatcherCreate watcherCreate = ApiWatcherCreateDTO.toWatcherCreate(watcherCreateDTO);
        int deptId = (int) watcherCreate.getDeptId();
        int courseId = (int) watcherCreate.getCourseId();
        Department department = departmentList.get(deptId);
        Course course = department.getCourseByIndex(courseId);

        Watcher watcher = new Watcher(department, course);
        manager.addWatcher(watcher);

        int watcherId = manager.getWatchers().indexOf(watcher);

        return ApiWatcherDTO.makeFromWatcher(watcher, departmentList, watcherId);
    }

    @GetMapping("/watchers/{id}")
    public ApiWatcherDTO getOneWatcher(@PathVariable("id") int watcherId) {
        Watcher watcher = manager.getWatcherById(watcherId);
        return ApiWatcherDTO.makeFromWatcher(watcher, departmentList, watcherId);
    }

    @DeleteMapping("/watchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatcher(@PathVariable("id") int watcherId) {
        manager.removeWatcherById(watcherId);
    }
}
