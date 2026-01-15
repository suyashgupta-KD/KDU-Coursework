package com.smartlock.system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnlockResponseDTO {

    private String message;
    private String user;
}
