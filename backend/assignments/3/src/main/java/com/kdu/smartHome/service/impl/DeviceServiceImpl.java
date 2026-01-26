package com.kdu.smartHome.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kdu.smartHome.dto.response.DeviceSummary;
import com.kdu.smartHome.dto.response.RoomsWithDevicesView;
import com.kdu.smartHome.dto.response.RoomWithDevicesResponse;
import com.kdu.smartHome.entity.Device;
import com.kdu.smartHome.entity.DeviceInventory;
import com.kdu.smartHome.entity.House;
import com.kdu.smartHome.entity.Room;
import com.kdu.smartHome.repository.DeviceInventoryRepository;
import com.kdu.smartHome.repository.DeviceRepository;
import com.kdu.smartHome.repository.HouseRepository;
import com.kdu.smartHome.repository.RoomRepository;
import com.kdu.smartHome.service.DeviceService;
import com.kdu.smartHome.service.HouseAccessService;
import com.kdu.smartHome.mapper.DeviceMapper;
import com.kdu.smartHome.exception.BadRequestException;
import com.kdu.smartHome.exception.ConflictException;
import com.kdu.smartHome.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of device operations.
 */

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceServiceImpl implements DeviceService {
        private final DeviceRepository deviceRepository;
        private final DeviceInventoryRepository inventoryRepository;
        private final RoomRepository roomRepository;
        private final HouseRepository houseRepository;
        private final HouseAccessService accessService;
        private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

        @Override
        public Device registerDevice(Long plotId, String kickstonId, String username, String password, Long userId) {
                accessService.requireAdmin(plotId, userId);

                DeviceInventory inventory = inventoryRepository
                                .findByKickstonIdAndDeviceUsernameAndDevicePassword(kickstonId, username, password)
                                .orElseThrow(() -> new BadRequestException("Invalid device credentials"));

                deviceRepository.findByInventoryKickstonIdAndDeletedDateIsNull(kickstonId)
                                .ifPresent(existing -> {
                                        throw new ConflictException("Device already registered");
                                });

                House house = houseRepository.findByPlotIdAndDeletedDateIsNull(plotId)
                                .orElseThrow(() -> new NotFoundException("House not found"));

                Device device = Device.builder()
                                .house(house)
                                .inventory(inventory)
                                .build();

                if (device.getVersion() == null) {
                        device.setVersion(0L);
                }

                Device saved = deviceRepository.save(device);
                log.info("Registered device {} to house {}", kickstonId, plotId);
                return saved;
        }

        @Override
        public Device assignRoom(Long plotId, Long deviceId, Long roomId, Long userId) {
                accessService.requireAdmin(plotId, userId);

                Device device = deviceRepository.findByDeviceIdAndHousePlotIdAndDeletedDateIsNull(deviceId, plotId)
                                .orElseThrow(() -> new NotFoundException("Device not found"));

                Room room = roomRepository.findByRoomIdAndHousePlotIdAndDeletedDateIsNull(roomId, plotId)
                                .orElseThrow(() -> new BadRequestException("Room not found in this house"));

                device.setRoom(room);
                Device saved = deviceRepository.save(device);
                log.info("Assigned device {} to room {} in house {}", deviceId, roomId, plotId);
                return saved;
        }

        @Override
        public Device moveDevice(Long plotId, Long deviceId, Long targetRoomId, Long userId) {
                accessService.requireMember(plotId, userId);

                Device device = deviceRepository.findByDeviceIdAndHousePlotIdAndDeletedDateIsNull(deviceId, plotId)
                                .orElseThrow(() -> new NotFoundException("Device not found"));

                Room targetRoom = roomRepository.findByRoomIdAndHousePlotIdAndDeletedDateIsNull(targetRoomId, plotId)
                                .orElseThrow(() -> new BadRequestException("Target room not found"));

                device.setRoom(targetRoom);
                Device saved = deviceRepository.save(device);
                log.info("Moved device {} to room {} in house {}", deviceId, targetRoomId, plotId);
                return saved;
        }

        @Override
        @Transactional(readOnly = true)
        public RoomsWithDevicesView roomsWithDevices(Long plotId, Long userId) {
                accessService.requireMember(plotId, userId);

                List<Room> rooms = roomRepository
                                .findByHousePlotIdAndDeletedDateIsNull(plotId,
                                                org.springframework.data.domain.Pageable.unpaged())
                                .getContent();
                List<Device> devices = deviceRepository.findByHousePlotIdAndDeletedDateIsNull(plotId);

                Map<Long, List<Device>> devicesByRoom = devices.stream()
                                .filter(d -> d.getRoom() != null)
                                .collect(Collectors.groupingBy(d -> d.getRoom().getRoomId()));

                List<RoomWithDevicesResponse> roomResponses = rooms.stream()
                                .map(room -> RoomWithDevicesResponse.builder()
                                                .roomId(room.getRoomId())
                                                .roomName(room.getName())
                                                .devices(devicesByRoom.getOrDefault(room.getRoomId(), List.of())
                                                                .stream()
                                                                .map(DeviceMapper::toSummary)
                                                                .toList())
                                                .build())
                                .toList();

                List<DeviceSummary> unassigned = devices.stream()
                                .filter(d -> d.getRoom() == null)
                                .map(DeviceMapper::toSummary)
                                .toList();

                return RoomsWithDevicesView.builder()
                                .rooms(roomResponses)
                                .unassignedDevices(unassigned)
                                .build();
        }

        @Override
        public void softDelete(Long deviceId, Long userId) {
                Device device = deviceRepository.findByDeviceIdAndDeletedDateIsNull(deviceId)
                                .orElseThrow(() -> new NotFoundException("Device not found"));
                accessService.requireAdmin(device.getHouse().getPlotId(), userId);

                device.setDeletedDate(Instant.now());
                deviceRepository.save(device);
                log.info("Soft deleted device {} in house {}", deviceId, device.getHouse().getPlotId());
        }
}
