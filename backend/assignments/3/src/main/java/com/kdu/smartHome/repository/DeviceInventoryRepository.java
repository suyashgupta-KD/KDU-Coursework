package com.kdu.smartHome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdu.smartHome.entity.DeviceInventory;

/**
 * JPA repository for immutable device inventory.
 * Inventory entries are the source of truth used to validate device registration.
 */

@Repository
public interface DeviceInventoryRepository extends JpaRepository<DeviceInventory, String> {
    /**
     * Validates provided credentials against the inventory record.
     */
    Optional<DeviceInventory> findByKickstonIdAndDeviceUsernameAndDevicePassword(String kickstonId, String username,
            String password);

    /**
     * Looks up an inventory record by primary key.
     */
    Optional<DeviceInventory> findByKickstonId(String kickstonId);
}
