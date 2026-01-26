package com.kdu.smartHome.dto.response;

import lombok.Builder;
import lombok.Value;

/**
 * Response payload for a device summary.
 */

@Value
@Builder
public class DeviceSummary {
    Long deviceId;
    String kickstonId;
    Long roomId;
}
