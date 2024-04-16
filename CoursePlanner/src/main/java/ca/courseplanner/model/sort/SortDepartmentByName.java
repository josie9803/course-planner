package ca.courseplanner.model.sort;

import ca.courseplanner.model.Department;

import java.util.Comparator;

/**
 * Class that use comparator to sort department
 */

public class SortDepartmentByName implements Comparator<Department> {

    @Override
    public int compare(Department o1, Department o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
