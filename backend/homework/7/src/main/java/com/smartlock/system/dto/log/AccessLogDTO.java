package com.smartlock.system.dto.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccessLogDTO {

    private final String user;
    private final String message;
}
