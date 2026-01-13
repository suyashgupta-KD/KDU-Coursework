package com.smartcity.hospital.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.smartcity.hospital.model.User;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setLoggedIn(rs.getBoolean("logged_in"));
        user.setTimezone(rs.getString("timezone"));
        user.setTenantId(rs.getLong("tenant_id"));
        return user;
    }
}
