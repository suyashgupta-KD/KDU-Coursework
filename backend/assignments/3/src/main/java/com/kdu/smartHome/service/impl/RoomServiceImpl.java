package com.kdu.smartHome.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdu.smartHome.entity.House;
import com.kdu.smartHome.entity.Room;
import com.kdu.smartHome.repository.HouseRepository;
import com.kdu.smartHome.repository.RoomRepository;
import com.kdu.smartHome.service.HouseAccessService;
import com.kdu.smartHome.service.RoomService;
import com.kdu.smartHome.exception.NotFoundException;
import com.kdu.smartHome.exception.ConflictException;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Default implementation of room operations.
 */

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final HouseAccessService accessService;
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final com.kdu.smartHome.repository.DeviceRepository deviceRepository;
    private static final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Override
    public Room createRoom(Long plotId, String name, Long userId) {
        accessService.requireAdmin(plotId, userId);

        House house = houseRepository.findByPlotIdAndDeletedDateIsNull(plotId)
                .orElseThrow(() -> new NotFoundException("House not found"));

        if (roomRepository.existsByHousePlotIdAndNameAndDeletedDateIsNull(plotId, name)) {
            throw new ConflictException("Room name already exists in this house");
        }

        Room room = Room.builder()
                .house(house)
                .name(name)
                .version(0L)
                .build();
        Room saved = roomRepository.save(room);
        log.info("Room {} created in house {} by user {}", saved.getRoomId(), plotId, userId);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> listRooms(Long plotId, Long userId, Pageable pageable) {
        accessService.requireMember(plotId, userId);
        return roomRepository.findByHousePlotIdAndDeletedDateIsNull(plotId, pageable);
    }

    @Override
    public void deleteRoom(Long plotId, Long roomId, Long userId) {
        accessService.requireAdmin(plotId, userId);

        Room room = roomRepository.findByRoomIdAndHousePlotIdAndDeletedDateIsNull(roomId, plotId)
                .orElseThrow(() -> new NotFoundException("Room not found"));

        room.setDeletedDate(java.time.Instant.now());
        roomRepository.save(room);

        deviceRepository.findByRoomRoomIdAndDeletedDateIsNull(roomId)
                .forEach(device -> {
                    device.setRoom(null);
                    deviceRepository.save(device);
                });
        log.info("Soft deleted room {} in house {} by user {}", roomId, plotId, userId);
    }
}
