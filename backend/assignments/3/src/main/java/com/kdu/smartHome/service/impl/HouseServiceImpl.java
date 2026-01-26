package com.kdu.smartHome.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdu.smartHome.entity.House;
import com.kdu.smartHome.entity.HouseMember;
import com.kdu.smartHome.entity.User;
import com.kdu.smartHome.model.HouseRole;
import com.kdu.smartHome.repository.HouseMemberRepository;
import com.kdu.smartHome.repository.HouseRepository;
import com.kdu.smartHome.repository.RoomRepository;
import com.kdu.smartHome.repository.DeviceRepository;
import com.kdu.smartHome.repository.UserRepository;
import com.kdu.smartHome.service.HouseService;
import com.kdu.smartHome.exception.NotFoundException;
import com.kdu.smartHome.exception.ForbiddenException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of house operations.
 */

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final HouseMemberRepository houseMemberRepository;
    private final RoomRepository roomRepository;
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(HouseServiceImpl.class);

    @Override
    public Page<House> listHousesForUser(Long userId, Pageable pageable) {
        return houseRepository
                .findDistinctByMembersUserUserIdAndMembersDeletedDateIsNullAndDeletedDateIsNull(userId, pageable);
    }

    @Override
    @Transactional
    public House createHouse(House house, Long userId) {
        User creator = userRepository.findByUserIdAndDeletedDateIsNull(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        house.setAdmin(creator);
        if (house.getVersion() == null) {
            house.setVersion(0L);
        }
        House savedHouse = houseRepository.save(house);
        log.info("House {} created by user {}", savedHouse.getPlotId(), userId);

        HouseMember adminMembership = HouseMember.builder()
                .house(savedHouse)
                .user(creator)
                .role(HouseRole.ADMIN)
                .build();
        houseMemberRepository.save(adminMembership);
        return savedHouse;
    }

    @Override
    @Transactional
    public House updateAddress(Long plotId, String address, Long userId) {
        House house = houseRepository.findByPlotIdAndDeletedDateIsNull(plotId)
                .orElseThrow(() -> new NotFoundException("House not found"));

        requireAdmin(plotId, userId);
        if (address != null && !address.isBlank()) {
            house.setAddress(address);
        }
        return houseRepository.save(house);
    }

    @Override
    @Transactional
    public void deleteHouse(Long id, Long userId) {
        House house = houseRepository.findByPlotIdAndDeletedDateIsNull(id)
                .orElseThrow(() -> new NotFoundException("House not found"));
        requireAdmin(id, userId);
        log.info("Soft delete house {} by user {}", id, userId);

        Instant now = Instant.now();
        house.setDeletedDate(now);
        houseRepository.save(house);

        houseMemberRepository.findByHousePlotIdAndDeletedDateIsNull(id)
                .forEach(member -> {
                    member.setDeletedDate(now);
                    houseMemberRepository.save(member);
                });

        roomRepository.findByHousePlotIdAndDeletedDateIsNull(id, org.springframework.data.domain.Pageable.unpaged())
                .getContent()
                .forEach(room -> {
                    room.setDeletedDate(now);
                    roomRepository.save(room);
                });

        deviceRepository.findByHousePlotIdAndDeletedDateIsNull(id)
                .forEach(device -> {
                    device.setDeletedDate(now);
                    deviceRepository.save(device);
                });
    }

    private void requireAdmin(Long plotId, Long userId) {
        houseMemberRepository.findByHousePlotIdAndUserUserIdAndDeletedDateIsNull(plotId, userId)
                .filter(member -> member.getRole() == HouseRole.ADMIN)
                .orElseThrow(() -> new ForbiddenException("Admin access required"));
    }
}
