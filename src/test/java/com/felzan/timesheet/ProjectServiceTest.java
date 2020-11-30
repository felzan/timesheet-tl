package com.felzan.timesheet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

    @BeforeEach
    public void setup() {
        when(securityContext.getAuthentication().getName())
                .thenReturn("email@email.com");
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithMockUser(username = "user@email.com")
    void shouldInsertHours() {
        HoursRequest hoursRequest = new HoursRequest(
                LocalTime.of(8, 0),
                LocalDate.of(2020, 12, 1));
        Integer projectId = 1;

        doNothing()
                .when(projectDAO).insertHours(any(HoursEntity.class));

        projectService.insertHours(hoursRequest, projectId);
    }

    @Test
    void shouldReturnSummary() {
        List<ProjectSummary> projectSummaries = singletonList(new ProjectSummary());
        when(projectDAO.summary()).thenReturn(projectSummaries);

        List<ProjectSummary> summary = projectService.summary();

        assertEquals(projectSummaries, summary);
    }
}