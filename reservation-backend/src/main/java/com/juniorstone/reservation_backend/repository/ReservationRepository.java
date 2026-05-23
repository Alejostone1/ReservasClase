package com.juniorstone.reservation_backend.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorstone.reservation_backend.entity.ReservationEntity;
import com.juniorstone.reservation_backend.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    /**
     * Checks whether a non-cancelled reservation already exists for the given date and time.
     *
     * @param date   reservation date
     * @param time   reservation time
     * @param status status to exclude from the check (typically {@code CANCELADO})
     * @return {@code true} if a matching reservation exists
     */
    boolean existsByDateAndTimeAndStatusNot(LocalDate date, LocalTime time, ReservationStatus status);
}
