package com.kdu.smartHome.service;

/**
 * Access checks for house membership and role.
 */

public interface HouseAccessService {
    /**
     * Requires the user to be an admin of the house.
     */
    void requireAdmin(Long plotId, Long userId);

    /**
     * Requires the user to be a member of the house.
     */
    void requireMember(Long plotId, Long userId);
}
