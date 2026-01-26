package com.kdu.smartHome.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for moving a device.
 */

@Getter
@Setter
public class MoveDeviceRequest {
    @NotNull
    private Long targetRoomId;
}
