package com.juniorstone.reservation_backend.service;

import java.util.List;

import com.juniorstone.reservation_backend.dto.request.CreateReservationRequest;
import com.juniorstone.reservation_backend.dto.response.ReservationResponse;
import com.juniorstone.reservation_backend.exception.ReservationBusinessRuleException;
import com.juniorstone.reservation_backend.exception.ReservationNotFoundException;

public interface ReservationService {

    /**
     * Retrieves all reservations from the database.
     *
     * @return list of all reservations
     */
    List<ReservationResponse> findAll();

    /**
     * Creates a reservation when no other active reservation exists for the same date and time.
     *
     * @param request validated reservation data
     * @return the created reservation
     * @throws ReservationBusinessRuleException if a reservation already exists for the same date and time
     */
    ReservationResponse create(CreateReservationRequest request);

    /**
     * Cancels the reservation identified by {@code id}.
     *
     * @param id the reservation primary key
     * @return the cancelled reservation
     * @throws ReservationNotFoundException if no reservation exists for {@code id}
     * @throws ReservationBusinessRuleException if the reservation is already cancelled
     */
    ReservationResponse cancel(Long id);
}
