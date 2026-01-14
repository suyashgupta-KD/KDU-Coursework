package com.hospital.staffing.model.response;

import lombok.Data;
import java.util.List;

@Data
public class UserPageResponse {

    // 1.
    private List<UserResponse> users;

    // 2.
    private int page;

    // 3.
    private int size;

    // 4.
    private long totalElements;

    // 5.
    private int totalPages;
}
