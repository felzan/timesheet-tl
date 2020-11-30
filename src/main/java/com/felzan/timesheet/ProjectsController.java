package com.felzan.timesheet;

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

    @GetMapping(value = "")
    public ResponseEntity<List<ProjectSummary>> getProjects() {
        return ResponseEntity.ok(projectService.summary());
    }

    @PostMapping(value = "/{projectId}/hours", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertHours(@PathVariable Integer projectId,
                                         @RequestBody HoursRequest hoursRequest) {
        projectService.insertHours(hoursRequest, projectId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
