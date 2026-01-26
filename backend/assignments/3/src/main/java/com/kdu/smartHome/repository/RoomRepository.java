package com.kdu.smartHome.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kdu.smartHome.entity.Room;

/**
 * JPA repository for rooms.
 */

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByHousePlotIdAndDeletedDateIsNull(Long plotId, Pageable pageable);

    Optional<Room> findByRoomIdAndHousePlotIdAndDeletedDateIsNull(Long roomId, Long plotId);

    boolean existsByHousePlotIdAndNameAndDeletedDateIsNull(Long plotId, String name);
}
