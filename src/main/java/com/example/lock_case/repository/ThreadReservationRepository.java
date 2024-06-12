package com.example.lock_case.repository;

import com.example.lock_case.entities.ThreadReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadReservationRepository extends JpaRepository<ThreadReservation, Long> {
}
