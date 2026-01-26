package com.kdu.smartHome.dto.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

/**
 * Response payload for device details.
 */

@Value
@Builder
public class DeviceResponse {

    Long kickston_id;
    String device_username;
    Instant manufacture_date_time;
    String manufacture_factory_place;

}
