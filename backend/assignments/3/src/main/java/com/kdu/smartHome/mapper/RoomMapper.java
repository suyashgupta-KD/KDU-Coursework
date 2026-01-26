package com.kdu.smartHome.mapper;

import com.kdu.smartHome.dto.response.RoomResponse;
import com.kdu.smartHome.entity.Room;

/**
 * Maps room entities to DTOs.
 */

public final class RoomMapper {
    private RoomMapper() {
    }

    public static RoomResponse toDto(Room room) {
        return RoomResponse.builder()
                .roomId(room.getRoomId())
                .name(room.getName())
                .build();
    }
}
