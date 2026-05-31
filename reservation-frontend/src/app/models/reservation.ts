/**
 * Interface representing a reservation in the system.
 */
export interface Reservation {
  id: number;
  nombreCliente: string;
  date: string;
  time: string;
  service: string;
  status: 'ACTIVA' | 'CANCELADA';
}

/**
 * Type for reservation status values.
 */
export type ReservationStatus = 'ACTIVA' | 'CANCELADA';
