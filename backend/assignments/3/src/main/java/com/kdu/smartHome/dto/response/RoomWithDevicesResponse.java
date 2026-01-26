package com.kdu.smartHome.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Value;

/**
 * Response payload for a room and its devices.
 */

@Value
@Builder
public class RoomWithDevicesResponse {
    Long roomId;
    String roomName;
    List<DeviceSummary> devices;
}
