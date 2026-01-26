package com.kdu.smartHome.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * House entity.
 */

@Entity
@Table(name = "houses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class House extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plot_id")
    private Long plotId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 255)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_user_id", nullable = false)
    private User admin;

    @Version
    @Column(nullable = false)
    private Long version;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    private List<Room> rooms;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    private List<HouseMember> members;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    private List<Device> devices;
}
