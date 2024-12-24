import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEdit, IconDefinition } from '@fortawesome/free-regular-svg-icons';
import { faAngleDoubleLeft, faAngleDoubleRight, faAngleLeft, faAngleRight, faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent {

  @Input('pageInfo') pageInfo!: any;
  @Input('pageSizes') pageSizes: number[] = [];
  @Input('currentPage') currentPage: number = 0;

  @Output() changeSize: EventEmitter<number> = new EventEmitter<number>();
  @Output() changeNumber: EventEmitter<number> = new EventEmitter<number>();

  @Input('columns') columns: any[] = [];
  @Input('data') data: any[] = [];

  @Output() edit: EventEmitter<string> = new EventEmitter<string>();
  @Output() delete: EventEmitter<string> = new EventEmitter<string>();


  public pageLimit: number = 3;

  public faEdit: IconDefinition = faEdit;
  public faDelete: IconDefinition = faTrash;
  public faRight: IconDefinition = faAngleRight;
  public faDoubleRight: IconDefinition = faAngleDoubleRight;
  public faLeft: IconDefinition = faAngleLeft;
  public faDoubleLeft: IconDefinition = faAngleDoubleLeft;

  public onEdit(id: string): void {
    this.edit.emit(id);
  }

  public onDelete(id: string): void {
    this.delete.emit(id);
  }

  public getPageList(): number[] {
    const start = Math.max(0, this.pageInfo?.number - this.pageLimit);
    const end = Math.min(this.pageInfo?.totalPages - 1, this.pageInfo?.number + this.pageLimit);
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  }

  public onChangeSize(size: any): void {
    this.changeSize.emit(size);
  }

  public onChangeNumber(item: number): void {
    this.changeNumber.emit(item);
  }



}
