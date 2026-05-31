package com.juniorstone.reservation_backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.juniorstone.reservation_backend.dto.request.CreateReservationRequest;
import com.juniorstone.reservation_backend.dto.response.ReservationResponse;
import com.juniorstone.reservation_backend.entity.ReservationEntity;
import com.juniorstone.reservation_backend.entity.ReservationStatus;
import com.juniorstone.reservation_backend.exception.ReservationBusinessRuleException;
import com.juniorstone.reservation_backend.exception.ReservationNotFoundException;
import com.juniorstone.reservation_backend.repository.ReservationRepository;
import com.juniorstone.reservation_backend.service.impl.ReservationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReservationService.
 */
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationServiceImpl(reservationRepository);
    }

    @Test
    void findAll_shouldReturnAllReservations() {
        // Given
        ReservationEntity entity1 = new ReservationEntity(
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut",
                ReservationStatus.ACTIVA
        );
        entity1.setId(1L);

        ReservationEntity entity2 = new ReservationEntity(
                "Jane Smith",
                LocalDate.of(2025, 1, 16),
                LocalTime.of(14, 0),
                "Massage",
                ReservationStatus.ACTIVA
        );
        entity2.setId(2L);

        when(reservationRepository.findAll()).thenReturn(List.of(entity1, entity2));

        // When
        List<ReservationResponse> result = reservationService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).nombreCliente());
        assertEquals("Jane Smith", result.get(1).nombreCliente());
        verify(reservationRepository).findAll();
    }

    @Test
    void create_shouldCreateReservationWhenNoConflictExists() {
        // Given
        CreateReservationRequest request = new CreateReservationRequest(
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut"
        );

        when(reservationRepository.existsByDateAndTimeAndStatusNot(
                any(LocalDate.class),
                any(LocalTime.class),
                eq(ReservationStatus.CANCELADA)
        )).thenReturn(false);

        ReservationEntity savedEntity = new ReservationEntity(
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut",
                ReservationStatus.ACTIVA
        );
        savedEntity.setId(1L);

        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(savedEntity);

        // When
        ReservationResponse result = reservationService.create(request);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("John Doe", result.nombreCliente());
        assertEquals(ReservationStatus.ACTIVA, result.status());
        verify(reservationRepository).existsByDateAndTimeAndStatusNot(
                eq(LocalDate.of(2025, 1, 15)),
                eq(LocalTime.of(10, 0)),
                eq(ReservationStatus.CANCELADA)
        );
        verify(reservationRepository).save(any(ReservationEntity.class));
    }

    @Test
    void create_shouldThrowExceptionWhenConflictExists() {
        // Given
        CreateReservationRequest request = new CreateReservationRequest(
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut"
        );

        when(reservationRepository.existsByDateAndTimeAndStatusNot(
                any(LocalDate.class),
                any(LocalTime.class),
                eq(ReservationStatus.CANCELADA)
        )).thenReturn(true);

        // When & Then
        assertThrows(ReservationBusinessRuleException.class, () -> {
            reservationService.create(request);
        });

        verify(reservationRepository, never()).save(any(ReservationEntity.class));
    }

    @Test
    void cancel_shouldCancelReservationWhenExistsAndActive() {
        // Given
        Long id = 1L;
        ReservationEntity entity = new ReservationEntity(
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut",
                ReservationStatus.ACTIVA
        );
        entity.setId(id);

        when(reservationRepository.findById(id)).thenReturn(Optional.of(entity));

        ReservationEntity cancelledEntity = new ReservationEntity(
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut",
                ReservationStatus.CANCELADA
        );
        cancelledEntity.setId(id);

        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(cancelledEntity);

        // When
        ReservationResponse result = reservationService.cancel(id);

        // Then
        assertNotNull(result);
        assertEquals(ReservationStatus.CANCELADA, result.status());
        verify(reservationRepository).findById(id);
        verify(reservationRepository).save(any(ReservationEntity.class));
    }

    @Test
    void cancel_shouldThrowExceptionWhenReservationNotFound() {
        // Given
        Long id = 1L;
        when(reservationRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.cancel(id);
        });

        verify(reservationRepository).findById(id);
        verify(reservationRepository, never()).save(any(ReservationEntity.class));
    }

    @Test
    void cancel_shouldThrowExceptionWhenAlreadyCancelled() {
        // Given
        Long id = 1L;
        ReservationEntity entity = new ReservationEntity(
                "John Doe",
                LocalDate.of(2025, 1, 15),
                LocalTime.of(10, 0),
                "Haircut",
                ReservationStatus.CANCELADA
        );
        entity.setId(id);

        when(reservationRepository.findById(id)).thenReturn(Optional.of(entity));

        // When & Then
        assertThrows(ReservationBusinessRuleException.class, () -> {
            reservationService.cancel(id);
        });

        verify(reservationRepository).findById(id);
        verify(reservationRepository, never()).save(any(ReservationEntity.class));
    }
}
