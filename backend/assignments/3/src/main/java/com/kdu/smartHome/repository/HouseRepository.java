package com.kdu.smartHome.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kdu.smartHome.entity.House;

/**
 * JPA repository for houses.
 * <p>
 * All queries implicitly respect soft delete by filtering {@code deletedDate IS NULL}.
 */

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    /**
     * Returns all active houses.
     */
    List<House> findAllByDeletedDateIsNull();

    /**
     * Finds a house by plot id if not soft deleted.
     */
    Optional<House> findByPlotIdAndDeletedDateIsNull(Long plotId);

    /**
     * Lists houses where the given user is a member (admin or member), paged.
     */
    Page<House> findDistinctByMembersUserUserIdAndMembersDeletedDateIsNullAndDeletedDateIsNull(Long userId,
            Pageable pageable);
}
