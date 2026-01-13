package com.smartcity.hospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedUserResponseDto {
    private List<UserResponseDto> users;
    private long totalCount;
    private int page;
    private int size;
}
