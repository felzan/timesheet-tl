package com.felzan.timesheet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.security.AccessControlException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @InjectMocks
    private ProjectService projectService;
    @Mock
    private ProjectDAO projectDAO;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private SecurityContext securityContext;
    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    void shouldInsertHours() {
        HoursRequest hoursRequest = new HoursRequest(
                LocalTime.of(8, 0),
                LocalDate.of(2020, 12, 1));
        Integer projectId = 1;

        when(securityContext.getAuthentication().getName())
                .thenReturn("email@email.com");
        SecurityContextHolder.setContext(securityContext);
        when(projectDAO.getUserAllocations("email@email.com", projectId))
                .thenReturn(optionalAllocationList(projectId));
        doNothing()
                .when(projectDAO).insertHours(any(HoursEntity.class));

        projectService.insertHours(hoursRequest, projectId);
    }

    @Test
    void shouldThrowRuntimeExceptionWhenInsertHoursWithEmptyAllocations() {
        HoursRequest hoursRequest = new HoursRequest(
                LocalTime.of(8, 0),
                LocalDate.of(2020, 12, 1));
        Integer projectId = 1;

        when(securityContext.getAuthentication().getName())
                .thenReturn("email@email.com");
        SecurityContextHolder.setContext(securityContext);
        when(projectDAO.getUserAllocations("email@email.com", projectId))
                .thenReturn(empty());

        assertThrows(RuntimeException.class, () ->
                projectService.insertHours(hoursRequest, projectId));
    }

    @Test
    void shouldThrowAccessControlExceptionWhenInsertHoursWithNoAllocations() {
        HoursRequest hoursRequest = new HoursRequest(
                LocalTime.of(8, 0),
                LocalDate.of(2020, 12, 1));
        Integer projectId = 1;

        when(securityContext.getAuthentication().getName())
                .thenReturn("email@email.com");
        SecurityContextHolder.setContext(securityContext);
        when(projectDAO.getUserAllocations("email@email.com", projectId))
                .thenReturn(of(emptyList()));

        assertThrows(AccessControlException.class, () ->
                projectService.insertHours(hoursRequest, projectId));
    }

    @Test
    void shouldReturnSummaryForAdmin() {
        Optional<List<ProjectSummary>> projectSummaries = of(singletonList(new ProjectSummary()));
        when(projectDAO.summary()).thenReturn(projectSummaries);

        when(httpServletRequest.isUserInRole(anyString()))
                .thenReturn(true);
        List<ProjectSummary> summary = projectService.summary();

        assertEquals(projectSummaries.get(), summary);
    }

    @Test
    void shouldReturnSummaryForUser() {
        when(securityContext.getAuthentication().getName())
                .thenReturn("email@email.com");
        SecurityContextHolder.setContext(securityContext);
        Optional<List<ProjectSummary>> projectSummaries = of(singletonList(new ProjectSummary()));
        when(projectDAO.summary("email@email.com"))
                .thenReturn(projectSummaries);
        when(httpServletRequest.isUserInRole(anyString()))
                .thenReturn(false);

        List<ProjectSummary> summary = projectService.summary();

        assertEquals(projectSummaries.get(), summary);
    }

    private Optional<List<Allocation>> optionalAllocationList(Integer projectId) {
        Allocation allocation = new Allocation();
        allocation.setProjectId(projectId);
        allocation.setUserId(1);
        return of(singletonList(allocation));
    }
}