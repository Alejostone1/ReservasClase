package com.juniorstone.reservation_backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juniorstone.reservation_backend.dto.request.CreateReservationRequest;
import com.juniorstone.reservation_backend.dto.response.ReservationResponse;
import com.juniorstone.reservation_backend.service.ReservationService;

/**
 * REST controller for managing reservations.
 * Exposes endpoints for creating, listing, and canceling reservations.
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * Creates the controller with the required service dependency.
     *
     * @param reservationService the reservation business logic service
     */
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Retrieves all reservations from the system.
     *
     * @return list of all reservations with HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    /**
     * Creates a new reservation.
     *
     * @param request the reservation data to create
     * @return the created reservation with HTTP 201 CREATED
     * @throws ReservationBusinessRuleException if a reservation already exists for the same date and time
     */
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody CreateReservationRequest request) {
        ReservationResponse response = reservationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Cancels a reservation by its identifier.
     *
     * @param id the reservation primary key
     * @return the cancelled reservation with HTTP 200 OK
     * @throws ReservationNotFoundException if no reservation exists for the given id
     * @throws ReservationBusinessRuleException if the reservation is already cancelled
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancel(id));
    }
}
