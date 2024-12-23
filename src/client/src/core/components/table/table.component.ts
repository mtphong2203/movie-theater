import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEdit, IconDefinition } from '@fortawesome/free-regular-svg-icons';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent {

  @Input('columns') columns: any[] = [];
  @Input('data') data: any[] = [];

  @Output() edit: EventEmitter<string> = new EventEmitter<string>();
  @Output() delete: EventEmitter<string> = new EventEmitter<string>();

  public faEdit: IconDefinition = faEdit;
  public faDelete: IconDefinition = faTrash;

  public onEdit(id: string): void {
    this.edit.emit(id);
  }

  public onDelete(id: string): void {
    this.delete.emit(id);
  }


}
