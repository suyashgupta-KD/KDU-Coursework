package com.kdu.smartHome.service.impl;

import org.springframework.stereotype.Service;

import com.kdu.smartHome.entity.HouseMember;
import com.kdu.smartHome.model.HouseRole;
import com.kdu.smartHome.repository.HouseMemberRepository;
import com.kdu.smartHome.service.HouseAccessService;
import com.kdu.smartHome.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;

/**
 * Default implementation of house access checks.
 */

@Service
@RequiredArgsConstructor
public class HouseAccessServiceImpl implements HouseAccessService {

    private final HouseMemberRepository houseMemberRepository;

    @Override
    public void requireAdmin(Long plotId, Long userId) {
        HouseMember member = houseMemberRepository
                .findByHousePlotIdAndUserUserIdAndDeletedDateIsNull(plotId, userId)
                .orElseThrow(() -> new ForbiddenException("Not a member of this house"));
        if (member.getRole() != HouseRole.ADMIN) {
            throw new ForbiddenException("Admin access required");
        }
    }

    @Override
    public void requireMember(Long plotId, Long userId) {
        houseMemberRepository.findByHousePlotIdAndUserUserIdAndDeletedDateIsNull(plotId, userId)
                .orElseThrow(() -> new ForbiddenException("Not a member of this house"));
    }
}
