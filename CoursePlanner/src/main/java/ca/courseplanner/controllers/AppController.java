package ca.courseplanner.controllers;

import ca.courseplanner.AllApiDtoClasses.*;
import ca.courseplanner.model.*;
import ca.courseplanner.model.filehandling.CsvFileReader;
import ca.courseplanner.model.filehandling.Printer;
import ca.courseplanner.model.watcher.Watcher;
import ca.courseplanner.model.watcher.WatcherCreate;
import ca.courseplanner.model.watcher.WatcherManager;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller layer to implement REST Web APIs
 */

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
        int departmentId = 0;
        for (Department department : departmentList) {
            ApiDepartmentDTO dto = ApiDepartmentDTO.makeFromDepartment(department, departmentId++);
            departmentDTO.add(dto);
        }
        return departmentDTO;
    }
    private Department getDepartmentByIdOrThrow(int departmentId){
        if (departmentId >= 0 && departmentId < departmentList.size()) {
            return departmentList.get(departmentId);
        }
        else{
            throw new IllegalArgumentException();
        }
    }
    private Course getCourseByIdOrThrow(Department department, int courseId){
        Course course = department.getCourseById(courseId);
        if (course == null){
            throw new IllegalArgumentException();
        }
        return course;
    }
    private CourseOffering getCourseOfferingByIdOrThrow(Course course, int courseOfferingId){
        CourseOffering courseOffering = course.getCourseOfferingById(courseOfferingId);
        if (courseOffering == null){
            throw new IllegalArgumentException();
        }
        return courseOffering;
    }
    @GetMapping("/departments/{id}/courses")
    public List<ApiCourseDTO> getCourses(@PathVariable("id") int departmentId) {
        Department department = getDepartmentByIdOrThrow(departmentId);

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
        Department department = getDepartmentByIdOrThrow(departmentId);
        Course course = getCourseByIdOrThrow(department, courseId);

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
        Department department = getDepartmentByIdOrThrow(departmentId);
        Course course = getCourseByIdOrThrow(department, courseId);
        CourseOffering offering = getCourseOfferingByIdOrThrow(course, offeringId);

        List<ApiOfferingSectionDTO> offeringSectionDTO = new ArrayList<>();
        for (OfferingSection section : offering.getOfferingSections()) {
            ApiOfferingSectionDTO dto = ApiOfferingSectionDTO.makeFromOfferingSection(section);
            offeringSectionDTO.add(dto);
        }
        return offeringSectionDTO;
    }

    @GetMapping("stats/students-per-semester")
    public List<ApiGraphDataPointDTO> getGraphData(@RequestParam(value="deptId") int deptId){
        Department department = getDepartmentByIdOrThrow(deptId);
        List<GraphDataPoint> graphDataPoints = department.createListOfDataPoint();

        List<ApiGraphDataPointDTO> graphDataPointDTOList = new ArrayList<>();
        for (GraphDataPoint graphDataPoint : graphDataPoints){
            graphDataPointDTOList.add(ApiGraphDataPointDTO.makeFromGraphDataPoint(graphDataPoint));
        }
        return graphDataPointDTOList;
    }

    @PostMapping("/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiOfferingSectionDTO addOffering(@RequestBody ApiOfferingDataDTO newOfferingData) {
        OfferingData offeringData = ApiOfferingDataDTO.toOfferingData(newOfferingData);
        dataFacade.processOfferingData(offeringData);

        Department department = dataFacade.findDepartment(offeringData.getSubjectName());
        Course course = department.findCourse(offeringData.getCatalogNumber());
        CourseOffering offering = course.findCourseOffering(offeringData.getSemester(), offeringData.getLocation());
        OfferingSection updatedSection = offering.findSection(offeringData.getComponent());
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
        Department department = getDepartmentByIdOrThrow(deptId);
        Course course = getCourseByIdOrThrow(department, courseId);

        Watcher watcher = new Watcher(department, course);
        manager.addWatcher(watcher);

        int watcherId = manager.getWatchers().indexOf(watcher);

        return ApiWatcherDTO.makeFromWatcher(watcher, departmentList, watcherId);
    }
    private Watcher getWatcherByIdOrThrow(int watcherId){
        Watcher watcher = manager.getWatcherById(watcherId);
        if (watcher == null){
            throw new IllegalArgumentException();
        }
        return watcher;
    }

    @GetMapping("/watchers/{id}")
    public ApiWatcherDTO getOneWatcher(@PathVariable("id") int watcherId) {
        Watcher watcher = getWatcherByIdOrThrow(watcherId);
        return ApiWatcherDTO.makeFromWatcher(watcher, departmentList, watcherId);
    }

    @DeleteMapping("/watchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatcher(@PathVariable("id") int watcherId) {
        try {
            manager.removeWatcherById(watcherId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {
    }
}
