package ca.courseplanner.model.watcher;

/**
 * Class that represents a newly created watcher
 */

public class WatcherCreate {
    private long deptId;
    private long courseId;

    public WatcherCreate(long deptId, long courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public long getDeptId() {
        return deptId;
    }

    public long getCourseId() {
        return courseId;
    }
}
