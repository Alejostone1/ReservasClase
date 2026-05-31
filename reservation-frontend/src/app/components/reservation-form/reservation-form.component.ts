import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule
} from '@angular/forms';

import { ReservationService } from '../../services/reservation.service';
import { ToastComponent } from '../toast/toast.component';

/**
 * Component for creating a new reservation.
 * Uses reactive forms with validation and displays errors via toast notifications.
 */
@Component({
  selector: 'app-reservation-form',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule, ToastComponent],
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.scss']
})
export class ReservationFormComponent {
  /**
   * The reactive form group for reservation data.
   */
  reservationForm: FormGroup;

  /**
   * List of available services for the dropdown.
   */
  services = [
    'Corte de Cabello',
    'Corte y Barba',
    'Coloración',
    'Tratamiento Capilar',
    'Peinado',
    'Manicure',
    'Pedicure'
  ];

  /**
   * Error message to display in the toast.
   */
  toastMessage = '';

  /**
   * Flag indicating if the toast should be visible.
   */
  showToast = false;

  /**
   * Flag indicating if the form is being submitted.
   */
  submitting = false;

  /**
   * Today's date in YYYY-MM-DD format for the date input min attribute.
   */
  today = new Date().toISOString().split('T')[0];

  /**
   * Creates the component with the required dependencies.
   *
   * @param fb the form builder for creating reactive forms
   * @param reservationService the reservation service for API communication
   */
  constructor(
    private fb: FormBuilder,
    private reservationService: ReservationService
  ) {
    this.reservationForm = this.fb.group({
      nombreCliente: ['', [Validators.required, Validators.minLength(2)]],
      date: ['', [Validators.required]],
      time: ['', [Validators.required]],
      service: ['', [Validators.required]]
    });
  }

  /**
   * Gets the nombreCliente form control.
   */
  get nombreCliente() {
    return this.reservationForm.get('nombreCliente');
  }

  /**
   * Gets the date form control.
   */
  get date() {
    return this.reservationForm.get('date');
  }

  /**
   * Gets the time form control.
   */
  get time() {
    return this.reservationForm.get('time');
  }

  /**
   * Gets the service form control.
   */
  get service() {
    return this.reservationForm.get('service');
  }

  /**
   * Checks if a form field has an error and has been touched.
   *
   * @param fieldName the name of the form field
   * @returns true if the field has an error and has been touched
   */
  hasError(fieldName: string): boolean {
    const field = this.reservationForm.get(fieldName);
    return field ? field.invalid && (field.dirty || field.touched) : false;
  }

  /**
   * Gets the error message for a form field.
   *
   * @param fieldName the name of the form field
   * @returns the error message or empty string
   */
  getErrorMessage(fieldName: string): string {
    const field = this.reservationForm.get(fieldName);
    if (!field || !field.errors) return '';

    if (field.errors['required']) {
      return 'Este campo es obligatorio';
    }

    if (field.errors['minlength']) {
      return `Mínimo ${field.errors['minlength'].requiredLength} caracteres`;
    }

    return 'Campo inválido';
  }

  /**
   * Submits the form to create a new reservation.
   */
  onSubmit(): void {
    if (this.reservationForm.invalid) {
      // Mark all fields as touched to show validation errors
      Object.keys(this.reservationForm.controls).forEach(key => {
        this.reservationForm.get(key)?.markAsTouched();
      });
      return;
    }

    this.submitting = true;

    const formValue = this.reservationForm.value;

    this.reservationService.createReservation(formValue).subscribe({
      next: () => {
        this.submitting = false;
        // Navigate back to the list
        window.location.href = '/reservations';
      },
      error: (error) => {
        this.submitting = false;
        this.toastMessage =
          error.error?.message ||
          'Error al crear la reserva. Por favor, verifique los datos e intente nuevamente.';
        this.showToast = true;
        console.error('Error creating reservation:', error);
      }
    });
  }

  /**
   * Hides the toast notification.
   */
  hideToast(): void {
    this.showToast = false;
  }

  /**
   * Resets the form to its initial state.
   */
  resetForm(): void {
    this.reservationForm.reset();
  }
}
