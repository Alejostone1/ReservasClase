package com.juniorstone.reservation_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.juniorstone.reservation_backend.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles reservation not found errors.
     *
     * @param ex the thrown exception
     * @return a not found error response
     */
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ReservationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), "NOT_FOUND"));
    }

    /**
     * Handles business rule violations.
     *
     * @param ex the thrown exception
     * @return a conflict error response
     */
    @ExceptionHandler(ReservationBusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(ReservationBusinessRuleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage(), "BUSINESS_RULE_VIOLATION"));
    }
}
