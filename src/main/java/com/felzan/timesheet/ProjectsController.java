package com.felzan.timesheet;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/projects", produces = APPLICATION_JSON_VALUE)
public class ProjectsController {

    @GetMapping(value = "")
    public ResponseEntity<List<String>> get() {
         String name = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return ResponseEntity.ok(Collections.singletonList(name));
    }
}
