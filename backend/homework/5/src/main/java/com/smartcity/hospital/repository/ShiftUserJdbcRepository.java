package com.smartcity.hospital.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShiftUserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShiftUserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void assignUserToShift(Long shiftId, Long userId, Long tenantId) {
        String sql = """
                    INSERT INTO shift_user (shift_id, user_id, tenant_id)
                    VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(sql, shiftId, userId, tenantId);
    }
}
