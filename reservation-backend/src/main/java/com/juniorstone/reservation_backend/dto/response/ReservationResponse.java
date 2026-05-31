package com.juniorstone.reservation_backend.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.juniorstone.reservation_backend.entity.ReservationStatus;

public record ReservationResponse(
        Long id,
        String nombreCliente,
        LocalDate date,
        LocalTime time,
        String service,
        ReservationStatus status) {
}
