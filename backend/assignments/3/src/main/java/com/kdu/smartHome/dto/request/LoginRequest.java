package com.kdu.smartHome.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for user login.
 */

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
