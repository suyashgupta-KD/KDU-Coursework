package com.kdu.smartHome.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdu.smartHome.entity.House;
import com.kdu.smartHome.entity.HouseMember;
import com.kdu.smartHome.entity.User;
import com.kdu.smartHome.model.HouseRole;
import com.kdu.smartHome.repository.HouseMemberRepository;
import com.kdu.smartHome.repository.HouseRepository;
import com.kdu.smartHome.repository.UserRepository;
import com.kdu.smartHome.service.HouseAccessService;
import com.kdu.smartHome.service.MemberService;
import com.kdu.smartHome.exception.ConflictException;
import com.kdu.smartHome.exception.NotFoundException;
import com.kdu.smartHome.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

/**
 * Default implementation of membership operations.
 */

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final HouseAccessService accessService;
    private final HouseRepository houseRepository;
    private final HouseMemberRepository houseMemberRepository;
    private final UserRepository userRepository;

    @Override
    public HouseMember addMember(Long plotId, Long targetUserId, Long actingUserId) {
        accessService.requireAdmin(plotId, actingUserId);

        House house = houseRepository.findByPlotIdAndDeletedDateIsNull(plotId)
                .orElseThrow(() -> new NotFoundException("House not found"));
        User user = userRepository.findByUserIdAndDeletedDateIsNull(targetUserId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        houseMemberRepository.findByHousePlotIdAndUserUserIdAndDeletedDateIsNull(plotId, targetUserId)
                .ifPresent(member -> {
                    throw new ConflictException("User already a member");
                });

        HouseMember member = HouseMember.builder()
                .house(house)
                .user(user)
                .role(HouseRole.MEMBER)
                .build();
        if (member.getVersion() == null) {
            member.setVersion(0L);
        }
        return houseMemberRepository.save(member);
    }

    @Override
    public void transferOwnership(Long plotId, Long newAdminUserId, Long actingUserId) {
        accessService.requireAdmin(plotId, actingUserId);

        House house = houseRepository.findByPlotIdAndDeletedDateIsNull(plotId)
                .orElseThrow(() -> new NotFoundException("House not found"));

        HouseMember newAdminMembership = houseMemberRepository
                .findByHousePlotIdAndUserUserIdAndDeletedDateIsNull(plotId, newAdminUserId)
                .orElseThrow(() -> new BadRequestException("New admin must be a member"));

        HouseMember currentAdminMembership = houseMemberRepository
                .findByHousePlotIdAndRoleAndDeletedDateIsNull(plotId, HouseRole.ADMIN)
                .orElseThrow(() -> new BadRequestException("Admin record missing"));

        newAdminMembership.setRole(HouseRole.ADMIN);
        currentAdminMembership.setRole(HouseRole.MEMBER);

        house.setAdmin(newAdminMembership.getUser());

        houseRepository.save(house);
        houseMemberRepository.save(newAdminMembership);
        houseMemberRepository.save(currentAdminMembership);
    }
}
