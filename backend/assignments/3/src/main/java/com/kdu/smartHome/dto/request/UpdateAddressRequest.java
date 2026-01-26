package com.kdu.smartHome.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for updating a house address.
 */

@Getter
@Setter
public class UpdateAddressRequest {
    /**
     * New address; optional for PATCH. When null/blank the address remains unchanged.
     */
    private String address;
}
