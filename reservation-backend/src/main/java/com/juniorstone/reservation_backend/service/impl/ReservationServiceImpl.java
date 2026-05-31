package com.juniorstone.reservation_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juniorstone.reservation_backend.dto.request.CreateReservationRequest;
import com.juniorstone.reservation_backend.dto.response.ReservationResponse;
import com.juniorstone.reservation_backend.entity.ReservationEntity;
import com.juniorstone.reservation_backend.entity.ReservationStatus;
import com.juniorstone.reservation_backend.exception.ReservationBusinessRuleException;
import com.juniorstone.reservation_backend.exception.ReservationNotFoundException;
import com.juniorstone.reservation_backend.repository.ReservationRepository;
import com.juniorstone.reservation_backend.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * Creates the service with the required repository dependency.
     *
     * @param reservationRepository reservation persistence gateway
     */
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ReservationResponse create(CreateReservationRequest request) {
        if (reservationRepository.existsByDateAndTimeAndStatusNot(
                request.date(), request.time(), ReservationStatus.CANCELADA)) {
            throw new ReservationBusinessRuleException(
                    "Ya existe una reserva para la fecha y hora indicadas");
        }

        var entity = new ReservationEntity(
                request.nombreCliente(),
                request.date(),
                request.time(),
                request.service(),
                ReservationStatus.ACTIVA);

        return toResponse(reservationRepository.save(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ReservationResponse cancel(Long id) {
        var entity = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (entity.getStatus() == ReservationStatus.CANCELADA) {
            throw new ReservationBusinessRuleException("La reserva ya está cancelada");
        }

        entity.setStatus(ReservationStatus.CANCELADA);
        return toResponse(reservationRepository.save(entity));
    }

    private ReservationResponse toResponse(ReservationEntity entity) {
        return new ReservationResponse(
                entity.getId(),
                entity.getNombreCliente(),
                entity.getDate(),
                entity.getTime(),
                entity.getService(),
                entity.getStatus());
    }
}
