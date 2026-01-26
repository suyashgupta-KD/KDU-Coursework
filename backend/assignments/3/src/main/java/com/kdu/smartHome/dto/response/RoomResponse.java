package com.kdu.smartHome.dto.response;

import lombok.Builder;
import lombok.Value;

/**
 * Response payload for room details.
 */

@Value
@Builder
public class RoomResponse {
    Long roomId;
    String name;
}
