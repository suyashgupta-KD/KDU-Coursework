package com.kdu.smartHome.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for creating a room.
 */

@Getter
@Setter
public class CreateRoomRequest {
    @NotBlank
    private String name;
}
