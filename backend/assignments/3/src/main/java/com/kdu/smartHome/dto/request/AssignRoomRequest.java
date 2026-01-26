package com.kdu.smartHome.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for assigning a device to a room.
 */

@Getter
@Setter
public class AssignRoomRequest {
    @NotNull
    private Long roomId;
}
