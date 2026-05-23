package com.juniorstone.reservation_backend.exception;

/**
 * Thrown when a reservation operation violates a business rule.
 */
public class ReservationBusinessRuleException extends RuntimeException {

    /**
     * Creates an exception with the given business rule message.
     *
     * @param message description of the violated rule
     */
    public ReservationBusinessRuleException(String message) {
        super(message);
    }
}
