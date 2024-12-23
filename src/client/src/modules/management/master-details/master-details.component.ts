import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { IconDefinition } from '@fortawesome/angular-fontawesome';
import { faSave } from '@fortawesome/free-regular-svg-icons';
import { faCancel } from '@fortawesome/free-solid-svg-icons';
import { RoleMasterDto } from '../../../models/role/role-master.model';

@Component({
  selector: 'app-master-details',
  standalone: true,
  imports: [],
  templateUrl: './master-details.component.html',
  styleUrl: './master-details.component.css'
})
export class MasterDetailsComponent<T> {

  @Output() cancel: EventEmitter<void> = new EventEmitter<void>();
  @Input('isEdit') isEdit: boolean = false;
  @Input('dataEdit') dataEdit: T | undefined;

  public faCancel: IconDefinition = faCancel;
  public faSave: IconDefinition = faSave;

  public form!: FormGroup;

}
