package com.kdu.smartHome.controller;

import com.kdu.smartHome.dto.request.AssignRoomRequest;
import com.kdu.smartHome.dto.request.MoveDeviceRequest;
import com.kdu.smartHome.dto.request.RegisterDeviceRequest;
import com.kdu.smartHome.dto.response.DeviceSummary;
import com.kdu.smartHome.dto.response.MessageResponse;
import com.kdu.smartHome.dto.response.RoomsWithDevicesView;
import com.kdu.smartHome.entity.Device;
import com.kdu.smartHome.mapper.DeviceMapper;
import com.kdu.smartHome.service.CurrentUserService;
import com.kdu.smartHome.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for device registration and movement.
 */

@RestController
@RequestMapping("/api/v1/houses/{plotId}")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final CurrentUserService currentUserService;
    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);

    /**
     * Registers a device to a house after inventory validation.
     */
    @PostMapping("/devices")
    public ResponseEntity<DeviceSummary> registerDevice(@PathVariable Long plotId,
            @Valid @RequestBody RegisterDeviceRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Register device {} for house {} by user {}", request.getKickstonId(), plotId, userId);
        Device saved = deviceService.registerDevice(plotId, request.getKickstonId(), request.getDeviceUsername(),
                request.getDevicePassword(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(DeviceMapper.toSummary(saved));
    }

    /**
     * Assigns a device to a room in the same house.
     */
    @PatchMapping("/devices/{deviceId}/assign-room")
    public ResponseEntity<DeviceSummary> assignRoom(@PathVariable Long plotId, @PathVariable Long deviceId,
            @Valid @RequestBody AssignRoomRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Assign device {} to room {} in house {} by user {}", deviceId, request.getRoomId(), plotId, userId);
        Device updated = deviceService.assignRoom(plotId, deviceId, request.getRoomId(), userId);
        return ResponseEntity.ok(DeviceMapper.toSummary(updated));
    }

    /**
     * Moves a device to another room in the same house.
     */
    @PatchMapping("/devices/{deviceId}/move")
    public ResponseEntity<DeviceSummary> moveDevice(@PathVariable Long plotId, @PathVariable Long deviceId,
            @Valid @RequestBody MoveDeviceRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Move device {} to room {} in house {} by user {}", deviceId, request.getTargetRoomId(), plotId,
                userId);
        Device updated = deviceService.moveDevice(plotId, deviceId, request.getTargetRoomId(), userId);
        return ResponseEntity.ok(DeviceMapper.toSummary(updated));
    }

    /**
     * Returns rooms with their assigned devices.
     */
    @GetMapping("/rooms-with-devices")
    public ResponseEntity<RoomsWithDevicesView> roomsWithDevices(@PathVariable Long plotId) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Fetch rooms with devices for house {} by user {}", plotId, userId);
        RoomsWithDevicesView view = deviceService.roomsWithDevices(plotId, userId);
        return ResponseEntity.ok(view);
    }

    /**
     * Soft deletes a device from a house.
     */
    @DeleteMapping("/devices/{deviceId}")
    public ResponseEntity<MessageResponse> deleteDevice(@PathVariable Long plotId, @PathVariable Long deviceId) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Delete device {} in house {} by user {}", deviceId, plotId, userId);
        deviceService.softDelete(deviceId, userId);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Device deleted")
                .build());
    }
}
