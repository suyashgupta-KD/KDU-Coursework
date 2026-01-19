package com.eventsphere.repository;

import com.eventsphere.entity.Reservation;
import com.eventsphere.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByIdAndUserId(Long id, Long userId);

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByUserIdAndEventId(Long userId, Long eventId);

    Optional<Reservation> findByIdAndUserIdAndEventId(Long id, Long userId, Long eventId);

    Optional<Reservation> findByIdAndStatus(Long id, ReservationStatus status);
}
