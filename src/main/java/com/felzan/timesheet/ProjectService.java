package com.felzan.timesheet;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.AccessControlException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private static final String CANNOT_INSERT_IN_THIS_PROJECT = "User cannot insert in this project";
    private static final String ROLE_ADMIN = "ADMIN";

    private final ProjectDAO projectDAO;
    private final HttpServletRequest httpServletRequest;

    public void insertHours(HoursRequest hoursRequest, Integer projectId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        canUserInsertToProjectValidation(username, projectId);

        HoursEntity entity = new HoursEntity();
        entity.setDay(hoursRequest.getDay());
        entity.setHours(hoursRequest.getHours());
        entity.setProjectId(projectId);
        entity.setUsername(username);

        projectDAO.insertHours(entity);
    }

    public List<ProjectSummary> summary() {
        boolean isAdmin = httpServletRequest.isUserInRole(ROLE_ADMIN);
        if (isAdmin) {
            return projectDAO.summary().orElseThrow(RuntimeException::new);
        } else {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return projectDAO.summary(username).orElseThrow(RuntimeException::new);
        }
    }

    private void canUserInsertToProjectValidation(String username, Integer projectId) {
        Optional<List<Allocation>> userAllocations = projectDAO.getUserAllocations(username, projectId);
        if (userAllocations.orElseThrow(RuntimeException::new).isEmpty()) {
            throw new AccessControlException(CANNOT_INSERT_IN_THIS_PROJECT);
        }
    }
}
