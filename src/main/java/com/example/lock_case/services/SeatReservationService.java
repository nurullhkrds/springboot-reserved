package com.example.lock_case.services;

import com.example.lock_case.entities.SeatReservation;
import com.example.lock_case.entities.ThreadReservation;
import com.example.lock_case.repository.SeatReservationRepository;
import com.example.lock_case.repository.ThreadReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SeatReservationService {

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    @Autowired
    private ThreadReservationRepository threadReservationRepository;

    @Transactional
    public boolean reserveSeat(String seatNumber, String reservedBy) {
        Optional<SeatReservation> optionalSeat = seatReservationRepository.findBySeatNumberForUpdate(seatNumber);

        if (optionalSeat.isPresent()) {
            SeatReservation seat = optionalSeat.get();
            if (!seat.isReserved()) {
                seat.setReserved(true);
                seat.setReservedBy(reservedBy);
                seat.setReservationTime(LocalDateTime.now());
                seatReservationRepository.save(seat);

                ThreadReservation threadReservation = new ThreadReservation();
                threadReservation.setThreadId(reservedBy);
                threadReservation.setSeatNumber(seatNumber);
                threadReservation.setReservationTime(LocalDateTime.now());
                threadReservationRepository.save(threadReservation);

                return true;
            }
        }
        return false;
    }
}
