package ca.courseplanner.AllApiDtoClasses;

import ca.courseplanner.model.WatcherCreate;

public final class ApiWatcherCreateDTO {
    public long deptId;
    public long courseId;

    public static WatcherCreate toWatcherCreate(ApiWatcherCreateDTO watcherCreateDTO) {
        return new WatcherCreate(watcherCreateDTO.deptId, watcherCreateDTO.courseId);
    }
}
