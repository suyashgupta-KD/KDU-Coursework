package com.kdu.smartHome.service;

import com.kdu.smartHome.dto.response.RoomsWithDevicesView;
import com.kdu.smartHome.entity.Device;

/**
 * Device operations.
 */

public interface DeviceService {
    /**
     * Registers a device to a house after inventory validation.
     */
    Device registerDevice(Long plotId, String kickstonId, String username, String password, Long userId);

    /**
     * Assigns a device to a room in the same house.
     */
    Device assignRoom(Long plotId, Long deviceId, Long roomId, Long userId);

    /**
     * Moves a device to another room in the same house.
     */
    Device moveDevice(Long plotId, Long deviceId, Long targetRoomId, Long userId);

    /**
     * Returns rooms with their assigned devices.
     */
    RoomsWithDevicesView roomsWithDevices(Long plotId, Long userId);

    /**
     * Soft deletes a device.
     */
    void softDelete(Long deviceId, Long userId);
}
