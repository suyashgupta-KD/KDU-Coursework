package com.kdu.smartHome.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for adding a house member.
 */

@Getter
@Setter
public class AddMemberRequest {
    @NotNull
    private Long userId;
}
