package com.kdu.smartHome.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for registering a device.
 */

@Getter
@Setter
public class RegisterDeviceRequest {
    @NotBlank
    private String kickstonId;

    @NotBlank
    private String deviceUsername;

    @NotBlank
    private String devicePassword;
}
