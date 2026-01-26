package com.kdu.smartHome.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for transferring house ownership.
 */

@Getter
@Setter
public class TransferOwnershipRequest {
    @NotNull
    private Long newAdminUserId;
}
