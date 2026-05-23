package com.juniorstone.reservation_backend.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
        @NotBlank String customerName,
        @NotNull @FutureOrPresent LocalDate date,
        @NotNull LocalTime time,
        @NotBlank String service) {
}
