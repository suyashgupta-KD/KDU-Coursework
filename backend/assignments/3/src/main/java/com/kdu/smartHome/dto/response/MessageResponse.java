package com.kdu.smartHome.dto.response;

import lombok.Builder;
import lombok.Value;

/**
 * Response payload for simple messages.
 */

@Value
@Builder
public class MessageResponse {
    String message;
}
