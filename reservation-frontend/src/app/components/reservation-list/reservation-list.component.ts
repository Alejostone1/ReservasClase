import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { Reservation } from '../../models/reservation';
import { ReservationService } from '../../services/reservation.service';

/**
 * Component for displaying a list of all reservations.
 * Shows reservations in a table format with cancel functionality.
 */
@Component({
  selector: 'app-reservation-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ReservationListComponent implements OnInit {
  /**
   * Array of reservations to display.
   */
  reservations: Reservation[] = [];

  /**
   * Flag indicating if data is being loaded.
   */
  loading = false;

  /**
   * Error message to display if an error occurs.
   */
  errorMessage = '';

  /**
   * Creates the component with the required service dependency.
   *
   * @param reservationService the reservation service for API communication
   * @param cdr the change detector reference for manual change detection
   */
  constructor(
    private reservationService: ReservationService,
    private cdr: ChangeDetectorRef
  ) {}

  /**
   * Initializes the component by loading reservations.
   */
  ngOnInit(): void {
    this.loadReservations();
  }

  /**
   * Loads all reservations from the backend.
   */
  loadReservations(): void {
    this.loading = true;
    this.errorMessage = '';

    this.reservationService.getReservations().subscribe({
      next: (data) => {
        this.reservations = data;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: (error) => {
        this.errorMessage = 'Error al cargar las reservas. Por favor, intente nuevamente.';
        this.loading = false;
        this.cdr.markForCheck();
        console.error('Error loading reservations:', error);
      }
    });
  }

  /**
   * Cancels a reservation by its identifier.
   *
   * @param id the reservation primary key
   */
  cancelReservation(id: number): void {
    if (!confirm('¿Está seguro de que desea cancelar esta reserva?')) {
      return;
    }

    this.reservationService.cancelReservation(id).subscribe({
      next: () => {
        // Reload the list after successful cancellation
        this.loadReservations();
      },
      error: (error) => {
        this.errorMessage = 'Error al cancelar la reserva. Por favor, intente nuevamente.';
        this.cdr.markForCheck();
        console.error('Error canceling reservation:', error);
      }
    });
  }

  /**
   * Gets the CSS class for the reservation status badge.
   *
   * @param status the reservation status
   * @returns the CSS class name
   */
  getStatusClass(status: string): string {
    switch (status) {
      case 'ACTIVA':
        return 'status-active';
      case 'CANCELADA':
        return 'status-cancelled';
      default:
        return 'status-unknown';
    }
  }
}
