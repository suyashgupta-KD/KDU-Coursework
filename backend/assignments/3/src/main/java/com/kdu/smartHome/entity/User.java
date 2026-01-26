package com.kdu.smartHome.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * User account entity.
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 120)
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<HouseMember> memberships;
}
