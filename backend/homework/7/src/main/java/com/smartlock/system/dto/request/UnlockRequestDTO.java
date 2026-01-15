package com.smartlock.system.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload for unlocking the smart lock.
 */
@Getter
@NoArgsConstructor
public class UnlockRequestDTO {

    private String user;
}
