package com.felzan.timesheet;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectDAO {

    private static final String INSERT_HOURS = "INSERT INTO hours (hours, `day`, project_id, user_id) VALUES (?, ?, ?, (SELECT id FROM users WHERE username = ?))";
    private static final String SUMMARY = "SELECT p.id as projectId, p.name, CONCAT_WS(':', (SUM( HOUR(hours) * 60 + MINUTE(hours)) / 60), (SUM( HOUR(hours) * 60 + MINUTE(hours)) % 60)) AS total FROM projects p LEFT JOIN hours h ON h.project_id = p.id GROUP BY p.name;";

    private static final BeanPropertyRowMapper<ProjectSummary> SUMMARY_ROW_MAPPER = BeanPropertyRowMapper.newInstance(ProjectSummary.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insertHours(HoursEntity entity) {
        jdbcTemplate.update(
                INSERT_HOURS,
                entity.getHours(), entity.getDay(), entity.getProjectId(), entity.getUsername()
        );
    }
    public List<ProjectSummary> summary() {
        return jdbcTemplate.query(SUMMARY, SUMMARY_ROW_MAPPER);
    }
}
