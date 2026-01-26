package com.kdu.smartHome.dto.response;

import lombok.Builder;
import lombok.Value;

/**
 * Response payload for house details.
 */

@Value
@Builder
public class HouseResponse {
    Long plotId;
    String name;
    String address;
}
