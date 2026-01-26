package com.kdu.smartHome.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdu.smartHome.entity.Device;

/**
 * JPA repository for devices.
 * <p>
 * Device uniqueness across active records is enforced by a partial unique index on {@code kickston_id WHERE deleted_date IS NULL}.
 */

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    /**
     * Lists all active devices.
     */
    List<Device> findAllByDeletedDateIsNull();

    /**
     * Finds a device by id if not soft deleted.
     */
    Optional<Device> findByDeviceIdAndDeletedDateIsNull(Long id);

    /**
     * Finds an active device tied to the given inventory key.
     */
    Optional<Device> findByInventoryKickstonIdAndDeletedDateIsNull(String kickstonId);

    /**
     * Finds an active device by id scoped to a house.
     */
    Optional<Device> findByDeviceIdAndHousePlotIdAndDeletedDateIsNull(Long deviceId, Long plotId);

    /**
     * Lists active devices in a house.
     */
    List<Device> findByHousePlotIdAndDeletedDateIsNull(Long plotId);

    /**
     * Lists active devices assigned to the given room.
     */
    List<Device> findByRoomRoomIdAndDeletedDateIsNull(Long roomId);
}
