package com.hospital.staffing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.staffing.entity.ShiftUser;

public interface ShiftUserRepository extends JpaRepository<ShiftUser, Long> {
}
