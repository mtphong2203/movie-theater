import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { IconDefinition } from '@fortawesome/angular-fontawesome';
import { faSearch, faPlus } from '@fortawesome/free-solid-svg-icons';
import { RoleMasterDto } from '../../../models/role/role-master.model';
import { PageInfo } from '../../../models/response-data.model';

@Component({
  selector: 'app-master-list',
  standalone: true,
  imports: [],
  templateUrl: './master-list.component.html',
  styleUrl: './master-list.component.css'
})
export class MasterListComponent<T> {
  public form!: FormGroup;

  public pageSizes: number[] = [5, 10, 15, 20, 30];

  public pageLimit: number = 3;
  public currentPage: number = 0;
  public currentPageSize: number = 5;
  public pageInfo!: PageInfo;

  public faSearch: IconDefinition = faSearch;
  public faPlus: IconDefinition = faPlus;

  public isShow: boolean = false;
  public isEdit: boolean = false;

  public data: T[] = [];
  public dataEdit: T | undefined;

  public response: string = '';
}
