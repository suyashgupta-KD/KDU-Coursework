package com.kdu.smartHome.service;

import com.kdu.smartHome.entity.HouseMember;

/**
 * Membership operations.
 */

public interface MemberService {
    /**
     * Adds a user to a house as a member.
     */
    HouseMember addMember(Long plotId, Long targetUserId, Long actingUserId);

    /**
     * Transfers house ownership to another member.
     */
    void transferOwnership(Long plotId, Long newAdminUserId, Long actingUserId);
}
