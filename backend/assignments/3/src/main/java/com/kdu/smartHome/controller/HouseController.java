package com.kdu.smartHome.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kdu.smartHome.dto.request.CreateHouseRequest;
import com.kdu.smartHome.dto.response.HouseResponse;
import com.kdu.smartHome.dto.request.UpdateAddressRequest;
import com.kdu.smartHome.dto.response.MessageResponse;
import com.kdu.smartHome.entity.House;
import com.kdu.smartHome.mapper.HouseMapper;
import com.kdu.smartHome.service.CurrentUserService;
import com.kdu.smartHome.service.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * REST endpoints for house management.
 */

@RestController
@RequestMapping("/api/v1/houses")
@AllArgsConstructor
public class HouseController {

    private final HouseService houseService;
    private final CurrentUserService currentUserService;
    private static final Logger log = LoggerFactory.getLogger(HouseController.class);

    /**
     * Lists houses for the current user.
     */
    @GetMapping
    public ResponseEntity<Page<HouseResponse>> listHouses(@PageableDefault(size = 20) Pageable pageable) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("List houses for user {}", userId);
        Page<HouseResponse> response = houseService
                .listHousesForUser(userId, pageable)
                .map(HouseMapper::toDto);

        return ResponseEntity.ok(response);
    }

    /**
     * Creates a house and makes the current user an admin.
     */
    @PostMapping
    public ResponseEntity<HouseResponse> createHouse(@Valid @RequestBody CreateHouseRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Create house for user {}", userId);
        House saved = houseService.createHouse(
                HouseMapper.toEntity(request), userId);

        return ResponseEntity.ok(HouseMapper.toDto(saved));
    }

    /**
     * Updates the address of a house.
     */
    @PatchMapping("/{plotId}/address")
    public ResponseEntity<HouseResponse> updateAddress(@PathVariable Long plotId,
            @Valid @RequestBody UpdateAddressRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Update house {} address by user {}", plotId, userId);
        House updatedHouse = houseService.updateAddress(plotId, request.getAddress(), userId);
        return ResponseEntity.ok(HouseMapper.toDto(updatedHouse));
    }

    /**
     * Soft deletes a house and related records.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteHouse(@PathVariable Long id) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Delete house {} by user {}", id, userId);
        houseService.deleteHouse(id, userId);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("House deleted")
                .build());
    }
}
