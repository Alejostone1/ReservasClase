import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

/**
 * Toast component for displaying error messages.
 * Shows a notification that can be dismissed by the user.
 */
@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.scss']
})
export class ToastComponent {
  /**
   * The message to display in the toast.
   */
  @Input() message: string = '';

  /**
   * Flag indicating if the toast is visible.
   */
  @Input() isVisible: boolean = false;

  /**
   * Type of toast (error, success, warning, info).
   */
  @Input() type: 'error' | 'success' | 'warning' | 'info' = 'error';

  @Output() hide = new EventEmitter<void>();

  /**
   * Hides the toast notification.
   */
  hideToast(): void {
    this.isVisible = false;
    this.hide.emit();
  }
}
