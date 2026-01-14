package com.hospital.staffing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.staffing.entity.ShiftType;

public interface ShiftTypeRepository extends JpaRepository<ShiftType, Long> {
}
