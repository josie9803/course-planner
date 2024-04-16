package ca.courseplanner.AllApiDtoClasses;

import ca.courseplanner.model.Department;

/**
 * Data Transfer Object that represent a department
 */

public class ApiDepartmentDTO {
    public long deptId;
    public String name;

    public static ApiDepartmentDTO makeFromDepartment(Department department, int deptId) {
        ApiDepartmentDTO departmentDTO = new ApiDepartmentDTO();
        departmentDTO.deptId = deptId;
        departmentDTO.name = department.getName();
        return departmentDTO;
    }
}
