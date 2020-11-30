package com.felzan.timesheet;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Repository
@RequiredArgsConstructor
public class ProjectDAO {

    private static final String INSERT_HOURS = "INSERT INTO hours (hours, `day`, project_id, user_id) VALUES (?, ?, ?, (SELECT id FROM users WHERE username = ?))";
    private static final String SUMMARY = "SELECT p.id as projectId, p.name, CONCAT_WS(':', (SUM( HOUR(hours) * 60 + MINUTE(hours)) / 60), (SUM( HOUR(hours) * 60 + MINUTE(hours)) % 60)) AS total FROM projects p LEFT JOIN hours h ON h.project_id = p.id";
    private static final String SUMMARY_BY_USERNAME = SUMMARY.concat(" LEFT JOIN users u ON u.id = h.user_id WHERE u.username = ? GROUP BY p.name;");
    private static final String SUMMARY_ADMIN = SUMMARY.concat(" GROUP BY p.name;");

    private static final String SELECT_USER_ALLOCATION_BY_USERNAME_AND_PROJECT_ID = "SELECT a.project_id as projectId, a.user_id as userId FROM allocation a LEFT JOIN users u ON u.id = a.user_id WHERE u.username = ? AND a.project_id = ?";

    private static final BeanPropertyRowMapper<ProjectSummary> SUMMARY_ROW_MAPPER = BeanPropertyRowMapper.newInstance(ProjectSummary.class);
    private static final BeanPropertyRowMapper<Allocation> ALLOCATION_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Allocation.class);

    private final JdbcTemplate jdbcTemplate;

    public void insertHours(HoursEntity entity) {
        jdbcTemplate.update(
                INSERT_HOURS,
                entity.getHours(), entity.getDay(), entity.getProjectId(), entity.getUsername()
        );
    }
    public Optional<List<ProjectSummary>> summary() {
        return of(jdbcTemplate.query(SUMMARY_ADMIN, SUMMARY_ROW_MAPPER));
    }

    public Optional<List<ProjectSummary>> summary(String username) {
        Object[] params = new Object[] {
                username
        };
        try {
            return of(jdbcTemplate.query(SUMMARY_BY_USERNAME, SUMMARY_ROW_MAPPER, params));
        } catch (EmptyResultDataAccessException e) {
            return empty();
        }
    }

    public Optional<List<Allocation>> getUserAllocations(String username, Integer projectId) {
        Object[] params = new Object[] {
                username,
                projectId
        };
        try {
            return of(jdbcTemplate.query(SELECT_USER_ALLOCATION_BY_USERNAME_AND_PROJECT_ID, ALLOCATION_ROW_MAPPER, params));
        } catch (EmptyResultDataAccessException e) {
            return empty();
        }
    }
}
