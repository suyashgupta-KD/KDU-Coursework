package com.eventsphere.repository;

import com.eventsphere.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByIdAndDeletedFalse(Long id);

    Page<Event> findByDeletedFalse(Pageable pageable);

    Page<Event> findByDeletedFalseAndAvailableTicketsGreaterThan(int availableTickets, Pageable pageable);

    boolean existsByNameIgnoreCase(String name);
}
