package com.juniorstone.reservation_backend.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.juniorstone.reservation_backend.dto.request.CreateReservationRequest;
import com.juniorstone.reservation_backend.dto.response.ReservationResponse;
import com.juniorstone.reservation_backend.entity.ReservationStatus;
import com.juniorstone.reservation_backend.service.ReservationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReservationController.
 */
@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        reservationController = new ReservationController(reservationService);
    }

    @Test
    void getAllReservations_shouldReturnOkWithReservations() {
        // Given
        ReservationResponse response1 = new ReservationResponse(
                1L,
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut",
                ReservationStatus.ACTIVA
        );

        ReservationResponse response2 = new ReservationResponse(
                2L,
                "Jane Smith",
                LocalDate.of(2025, 1, 16),
                LocalTime.of(14, 0),
                "Massage",
                ReservationStatus.ACTIVA
        );

        when(reservationService.findAll()).thenReturn(List.of(response1, response2));

        // When
        ResponseEntity<List<ReservationResponse>> result = reservationController.getAllReservations();

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        verify(reservationService).findAll();
    }

    @Test
    void createReservation_shouldReturnCreatedWithReservation() {
        // Given
        CreateReservationRequest request = new CreateReservationRequest(
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut"
        );

        ReservationResponse response = new ReservationResponse(
                1L,
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut",
                ReservationStatus.ACTIVA
        );

        when(reservationService.create(any(CreateReservationRequest.class))).thenReturn(response);

        // When
        ResponseEntity<ReservationResponse> result = reservationController.createReservation(request);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().id());
        verify(reservationService).create(request);
    }

    @Test
    void cancelReservation_shouldReturnOkWithCancelledReservation() {
        // Given
        Long id = 1L;
        ReservationResponse response = new ReservationResponse(
                1L,
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut",
                ReservationStatus.CANCELADA
        );

        when(reservationService.cancel(id)).thenReturn(response);

        // When
        ResponseEntity<ReservationResponse> result = reservationController.cancelReservation(id);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(ReservationStatus.CANCELADA, result.getBody().status());
        verify(reservationService).cancel(id);
    }
}
