package com.kdu.smartHome.service;

import com.kdu.smartHome.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * House operations.
 */

public interface HouseService {
    /**
     * Lists houses for the given user.
     */
    Page<House> listHousesForUser(Long userId, Pageable pageable);

    /**
     * Creates a house and assigns the user as admin.
     */
    House createHouse(House house, Long userId);

    /**
     * Updates a house address.
     */
    House updateAddress(Long plotId, String address, Long userId);

    /**
     * Soft deletes a house and related data.
     */
    void deleteHouse(Long id, Long userId);
}
