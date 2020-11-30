package com.felzan.timesheet;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectDAO projectDAO;

    public void insertHours(HoursRequest hoursRequest, Integer projectId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        HoursEntity entity = new HoursEntity();
        entity.setDay(hoursRequest.getDay());
        entity.setHours(hoursRequest.getHours());
        entity.setProjectId(projectId);
        entity.setUsername(currentUsername);

        projectDAO.insertHours(entity);
    }

    public List<ProjectSummary> summary() {
        return projectDAO.summary();
    }
}
