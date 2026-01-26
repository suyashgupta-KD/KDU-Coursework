package com.kdu.smartHome.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdu.smartHome.entity.HouseMember;
import com.kdu.smartHome.model.HouseRole;

/**
 * JPA repository for house members.
 */

@Repository
public interface HouseMemberRepository extends JpaRepository<HouseMember, Long> {
    Optional<HouseMember> findByHousePlotIdAndUserUserIdAndDeletedDateIsNull(Long plotId, Long userId);

    List<HouseMember> findByHousePlotIdAndDeletedDateIsNull(Long plotId);

    Optional<HouseMember> findByHousePlotIdAndRoleAndDeletedDateIsNull(Long plotId, HouseRole role);
}
