package com.smartcity.hospital.repository;

import com.smartcity.hospital.mapper.UserRowMapper;
import com.smartcity.hospital.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // INSERT
    public void save(String username, String timezone, Long tenantId) {
        String sql = """
                    INSERT INTO users (username, timezone, tenant_id)
                    VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql, username, timezone, tenantId);
    }

    // PAGINATED FETCH
    public List<User> findUsers(
            Long tenantId,
            int limit,
            int offset,
            String sortDir) {
        String direction = sortDir.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        String sql = """
                    SELECT id, username, logged_in, timezone, tenant_id
                    FROM users
                    WHERE tenant_id = ?
                    ORDER BY username %s
                    LIMIT ? OFFSET ?
                """.formatted(direction);

        return jdbcTemplate.query(
                sql,
                new UserRowMapper(),
                tenantId,
                limit,
                offset);
    }

    // COUNT (REQUIRED FOR RESPONSE DTO)
    public long countByTenant(Long tenantId) {
        String sql = "SELECT COUNT(*) FROM users WHERE tenant_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, tenantId);
    }

    // UPDATE
    public void updateUser(Long userId, String username, String timezone) {
        String sql = """
                    UPDATE users
                    SET username = ?, timezone = ?
                    WHERE id = ?
                """;
        jdbcTemplate.update(sql, username, timezone, userId);
    }
}
