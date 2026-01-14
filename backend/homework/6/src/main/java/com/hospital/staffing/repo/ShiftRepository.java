package com.hospital.staffing.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import com.hospital.staffing.entity.Shift;
import java.time.LocalDate;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    // 1.
    @Query("""
                SELECT s FROM Shift s
                WHERE s.startDate = :startDate
                AND s.endDate <= :endDate
                ORDER BY s.shiftName ASC
            """)
    List<Shift> findTopShifts(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable);
}
