package com.kdu.smartHome.controller;

import com.kdu.smartHome.dto.request.CreateRoomRequest;
import com.kdu.smartHome.dto.response.RoomResponse;
import com.kdu.smartHome.entity.Room;
import com.kdu.smartHome.service.CurrentUserService;
import com.kdu.smartHome.service.RoomService;
import com.kdu.smartHome.mapper.RoomMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * REST endpoints for room management.
 */

@RestController
@RequestMapping("/api/v1/houses/{plotId}/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final CurrentUserService currentUserService;
    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    /**
     * Creates a room in a house.
     */
    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@PathVariable Long plotId,
            @Valid @RequestBody CreateRoomRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Create room '{}' in house {} by user {}", request.getName(), plotId, userId);
        Room saved = roomService.createRoom(plotId, request.getName(), userId);
        return ResponseEntity.ok(RoomMapper.toDto(saved));
    }

    /**
     * Lists rooms in a house.
     */
    @GetMapping
    public ResponseEntity<Page<RoomResponse>> listRooms(@PathVariable Long plotId,
            @PageableDefault(size = 20) Pageable pageable) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("List rooms for house {} by user {}", plotId, userId);
        Page<RoomResponse> rooms = roomService.listRooms(plotId, userId, pageable)
                .map(RoomMapper::toDto);
        return ResponseEntity.ok(rooms);
    }

    /**
     * Soft deletes a room and unassigns its devices.
     */
    @DeleteMapping("/{roomId}")
    public ResponseEntity<com.kdu.smartHome.dto.response.MessageResponse> deleteRoom(
            @PathVariable Long plotId,
            @PathVariable Long roomId) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Delete room {} in house {} by user {}", roomId, plotId, userId);
        roomService.deleteRoom(plotId, roomId, userId);
        return ResponseEntity.ok(com.kdu.smartHome.dto.response.MessageResponse.builder()
                .message("Room deleted")
                .build());
    }
}
