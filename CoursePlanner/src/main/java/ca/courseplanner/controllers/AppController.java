package ca.courseplanner.controllers;

import ca.courseplanner.AllApiDtoClasses.ApiAboutDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/api/about")
    public ApiAboutDTO getAboutInfo() {
        ApiAboutDTO aboutInfo = new ApiAboutDTO("Amazing course planner", "Nathan & Josie");
        return aboutInfo;
    }
}
