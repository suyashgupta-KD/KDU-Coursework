package com.kdu.smartHome.mapper;

import com.kdu.smartHome.dto.response.DeviceSummary;
import com.kdu.smartHome.entity.Device;

/**
 * Maps device entities to DTOs.
 */

public final class DeviceMapper {
    private DeviceMapper() {
    }

    public static DeviceSummary toSummary(Device device) {
        Long roomId = device.getRoom() != null ? device.getRoom().getRoomId() : null;
        return DeviceSummary.builder()
                .deviceId(device.getDeviceId())
                .kickstonId(device.getInventory().getKickstonId())
                .roomId(roomId)
                .build();
    }
}
