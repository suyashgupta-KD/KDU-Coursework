package com.eventsphere.repository;

import com.eventsphere.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByIdAndUserId(Long id, Long userId);

    Optional<Booking> findByReservationId(Long reservationId);

    List<Booking> findByUserId(Long userId);
}
