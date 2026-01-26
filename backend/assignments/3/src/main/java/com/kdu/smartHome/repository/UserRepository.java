package com.kdu.smartHome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdu.smartHome.entity.User;

/**
 * JPA repository for users.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndDeletedDateIsNull(String email);

    Optional<User> findByUserIdAndDeletedDateIsNull(Long userId);

    boolean existsByEmailAndDeletedDateIsNull(String email);
}
