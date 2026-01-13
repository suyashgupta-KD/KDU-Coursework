package com.smartcity.hospital.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String timezone;
    private Long tenantId;
}
