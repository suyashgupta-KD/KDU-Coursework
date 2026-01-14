package com.hospital.staffing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.staffing.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
