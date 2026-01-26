package com.kdu.smartHome.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Value;

/**
 * Response payload for rooms with devices.
 */

@Value
@Builder
public class RoomsWithDevicesView {
    List<RoomWithDevicesResponse> rooms;
    List<DeviceSummary> unassignedDevices;
}
