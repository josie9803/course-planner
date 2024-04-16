package ca.courseplanner.AllApiDtoClasses;

/**
 * Data Transfer Object that represent a introduction of the app
 */

public class ApiAboutDTO {
    public String appName;
    public String authorName;

    public ApiAboutDTO(String appName, String authorName) {
        this.appName = appName;
        this.authorName = authorName;
    }
}
