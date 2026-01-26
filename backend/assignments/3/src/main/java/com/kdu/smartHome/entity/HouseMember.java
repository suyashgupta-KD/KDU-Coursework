package com.kdu.smartHome.entity;

import com.kdu.smartHome.model.HouseRole;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "house_members", uniqueConstraints = {
        @UniqueConstraint(name = "uq_house_member", columnNames = { "plot_id", "user_id" })
})

/**
 * House membership entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plot_id", nullable = false)
    private House house;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private HouseRole role;

    @Version
    @Column(nullable = false)
    private Long version;
}
