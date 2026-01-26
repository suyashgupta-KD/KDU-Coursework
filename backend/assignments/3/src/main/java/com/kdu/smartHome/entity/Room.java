package com.kdu.smartHome.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rooms", uniqueConstraints = {
        @UniqueConstraint(name = "uq_room_name_per_house", columnNames = { "plot_id", "name" })
})

/**
 * Room entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plot_id", nullable = false)
    private House house;

    @Column(nullable = false, length = 120)
    private String name;

    @Version
    @Column(nullable = false)
    private Long version;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Device> devices;
}
