package com.example.lock_case.repository;

import com.example.lock_case.entities.SeatReservation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SeatReservation s WHERE s.seatNumber = :seatNumber")
    Optional<SeatReservation> findBySeatNumberForUpdate(@Param("seatNumber") String seatNumber);
}
