package com.felzan.timesheet;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/projects", produces = APPLICATION_JSON_VALUE)
public class ProjectsController {

    private final ProjectService projectService;

    @ApiResponse(description = "Returns a summary of hours by project." +
            "<br>Filter by which projects an user is allocated.")
    @GetMapping
    public ResponseEntity<List<ProjectSummary>> getProjects() {
        return ResponseEntity.ok(projectService.summary());
    }

    @ApiResponse(description = "Add hours to a project")
    @PostMapping(value = "/{projectId}/hours", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertHours(@PathVariable Integer projectId,
                                         @RequestBody HoursRequest hoursRequest) {
        projectService.insertHours(hoursRequest, projectId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
