package com.hospital.staffing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.staffing.entity.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
