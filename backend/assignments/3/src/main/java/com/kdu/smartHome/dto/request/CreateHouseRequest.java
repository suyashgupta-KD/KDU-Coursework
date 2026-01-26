package com.kdu.smartHome.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for creating a house.
 */

@Getter
@Setter
public class CreateHouseRequest {

    @NotBlank
    @NotNull
    String name;

    @NotBlank
    @NotNull
    String address;
}
