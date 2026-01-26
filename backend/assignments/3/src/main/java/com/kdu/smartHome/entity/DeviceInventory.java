package com.kdu.smartHome.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Device inventory record entity.
 */

@Entity
@Table(name = "device_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceInventory extends BaseEntity {

    @Id
    @Column(name = "kickston_id", length = 6)
    private String kickstonId;

    @Column(name = "device_username", nullable = false, length = 120)
    private String deviceUsername;

    @Column(name = "device_password", nullable = false, length = 120)
    private String devicePassword;

    @Column(name = "manufacture_date_time", nullable = false)
    private Instant manufactureDateTime;

    @Column(name = "manufacture_factory_place", nullable = false, length = 120)
    private String manufactureFactoryPlace;
}
