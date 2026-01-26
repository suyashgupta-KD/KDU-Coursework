package com.kdu.smartHome.dto.response;

import lombok.Builder;
import lombok.Value;

/**
 * Response payload for API errors.
 */

@Value
@Builder
public class ApiErrorResponse {
    String message;
}
