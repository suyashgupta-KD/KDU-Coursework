package com.kdu.smartHome.dto.request;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for creating a device.
 */

@Getter
@Setter
public class CreateDeviceRequest {

    @NotBlank
    private String device_username;

    @NotBlank
    private String device_password;

    @NotNull
    @PastOrPresent
    private Instant manufacture_date_time;

    @NotBlank
    private String manufacture_factory_place;

}
