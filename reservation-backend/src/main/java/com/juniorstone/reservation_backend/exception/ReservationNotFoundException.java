package com.juniorstone.reservation_backend.exception;

/**
 * Thrown when a reservation cannot be found by its identifier.
 */
public class ReservationNotFoundException extends RuntimeException {

    /**
     * Creates an exception for the given reservation identifier.
     *
     * @param id the reservation primary key
     */
    public ReservationNotFoundException(Long id) {
        super("No se encontró la reserva con id: " + id);
    }
}
