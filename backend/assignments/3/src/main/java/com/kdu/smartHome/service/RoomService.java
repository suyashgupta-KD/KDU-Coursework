package com.kdu.smartHome.service;

import com.kdu.smartHome.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Room operations.
 */

public interface RoomService {
    /**
     * Creates a room in a house.
     */
    Room createRoom(Long plotId, String name, Long userId);

    /**
     * Lists rooms for a house.
     */
    Page<Room> listRooms(Long plotId, Long userId, Pageable pageable);

    /**
     * Soft deletes a room and unassigns its devices.
     */
    void deleteRoom(Long plotId, Long roomId, Long userId);
}
