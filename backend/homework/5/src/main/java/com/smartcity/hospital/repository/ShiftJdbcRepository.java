package com.smartcity.hospital.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartcity.hospital.model.Shift;

@Repository
public class ShiftJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShiftJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Shift shift) {
        String sql = """
                    INSERT INTO shift (
                        shift_type_id, start_date, end_date,
                        start_time, end_time, tenant_id
                    )
                    VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                shift.getShiftTypeId(),
                shift.getStartDate(),
                shift.getEndDate(),
                shift.getStartTime(),
                shift.getEndTime(),
                shift.getTenantId());
    }
}
