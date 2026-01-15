package com.smartlock.system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Attempts to unlock the smart lock for a given user.
 *
 * @param request payload containing the user attempting access
 * @return response indicating the result of the unlock attempt
 */
@Getter
@AllArgsConstructor
public class UnlockResponseDTO {

    private String message;
    private String user;
}
