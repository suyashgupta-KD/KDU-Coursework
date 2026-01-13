package com.smartcity.hospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private Boolean loggedIn;
    private String timezone;
    private Long tenantId;
}
