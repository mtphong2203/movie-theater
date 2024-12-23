import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { IconDefinition } from '@fortawesome/angular-fontawesome';
import { faSearch, faPlus } from '@fortawesome/free-solid-svg-icons';
import { RoleMasterDto } from '../../../models/role/role-master.model';

@Component({
  selector: 'app-master-list',
  standalone: true,
  imports: [],
  templateUrl: './master-list.component.html',
  styleUrl: './master-list.component.css'
})
export class MasterListComponent<T> {
  public form!: FormGroup;

  public faSearch: IconDefinition = faSearch;
  public faPlus: IconDefinition = faPlus;

  public isShow: boolean = false;
  public isEdit: boolean = false;

  public data: T[] = [];
  public dataEdit: T | undefined;

  public response: string = '';
}
