package com.kdu.smartHome.dto.response;

import lombok.Builder;
import lombok.Value;

/**
 * Response payload for authentication.
 */

@Value
@Builder
public class LoginResponse {
    String message;
    String token;
    Long userId;
}
