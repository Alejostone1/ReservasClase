import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

import { Reservation } from '../models/reservation';

/**
 * Service for managing reservations through the backend API.
 * Provides methods to create, retrieve, and cancel reservations.
 */
@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private readonly apiUrl = `${environment.apiUrl}/reservas`;

  /**
   * Creates the service with the required HTTP client dependency.
   *
   * @param http the Angular HTTP client for making API requests
   */
  constructor(private http: HttpClient) {}

  /**
   * Retrieves all reservations from the backend.
   *
   * @returns Observable emitting the list of all reservations
   */
  getReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.apiUrl);
  }

  /**
   * Creates a new reservation.
   *
   * @param reservation the reservation data to create
   * @returns Observable emitting the created reservation
   */
  createReservation(reservation: Omit<Reservation, 'id' | 'status'>): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, reservation);
  }

  /**
   * Cancels a reservation by its identifier.
   *
   * @param id the reservation primary key
   * @returns Observable emitting the cancelled reservation
   */
  cancelReservation(id: number): Observable<Reservation> {
    return this.http.delete<Reservation>(`${this.apiUrl}/${id}`);
  }
}
