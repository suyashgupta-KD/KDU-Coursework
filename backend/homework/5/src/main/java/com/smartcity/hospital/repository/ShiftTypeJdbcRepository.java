package com.smartcity.hospital.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.smartcity.hospital.model.ShiftType;

@Repository
public class ShiftTypeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShiftTypeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ShiftType shiftType) {
        String sql = """
                    INSERT INTO shift_type (name, description, active, tenant_id)
                    VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                shiftType.getName(),
                shiftType.getDescription(),
                shiftType.getActive(),
                shiftType.getTenantId());
    }
}
