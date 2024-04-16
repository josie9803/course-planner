package ca.courseplanner.AllApiDtoClasses;
import ca.courseplanner.model.Department;
import ca.courseplanner.model.watcher.Watcher;

import java.util.List;

/**
 * Data Transfer Object that represent a watcher
 */

public class ApiWatcherDTO {
    public long id;
    public ApiDepartmentDTO department;
    public ApiCourseDTO course;
    public List<String> events;

    public static ApiWatcherDTO makeFromWatcher(Watcher watcher, List<Department> departmentList, int id) {
        ApiWatcherDTO watcherDTO = new ApiWatcherDTO();
        watcherDTO.id = id;

        int deptId = departmentList.indexOf(watcher.getDepartment());
        int courseId = watcher.getDepartment().getCourseList().indexOf(watcher.getCourse());

        watcherDTO.department = ApiDepartmentDTO.makeFromDepartment(watcher.getDepartment(), deptId);
        watcherDTO.course = ApiCourseDTO.makeFromCourse(watcher.getCourse(), courseId);
        watcherDTO.events = watcher.getEvents();
        return watcherDTO;
    }
}
