import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ROLE_SERVICE } from '../../../constants/injection.const';
import { IRoleService } from '../../../services/role/role-service.interface';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { faPlus, faSearch, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { RoleDetailsComponent } from "./role-details/role-details.component";
import { TableComponent } from "../../../core/components/table/table.component";
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-role-list',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule, CommonModule, RoleDetailsComponent, TableComponent],
  templateUrl: './role-list.component.html',
  styleUrl: './role-list.component.css'
})
export class RoleListComponent implements OnInit {

  public form!: FormGroup;

  public faSearch: IconDefinition = faSearch;
  public faPlus: IconDefinition = faPlus;

  public isShow: boolean = false;
  public isEdit: boolean = false;

  public data: any[] = [];
  public dataEdit: any;

  public response: string = '';

  public columns: any[] = [
    { name: 'name', title: 'Title' },
    { name: 'description', title: 'Description' },
    { name: 'active', title: 'Active' },
  ]

  constructor(@Inject(ROLE_SERVICE) private roleService: IRoleService) { }

  ngOnInit(): void {
    this.createForm();
    this.search();
  }

  private createForm(): void {
    this.form = new FormGroup({
      keyword: new FormControl('', Validators.maxLength(50)),
    });
  }

  private search(): void {
    const param: any = {
      keyword: this.form.value.keyword,
    }
    this.roleService.search(param).subscribe((result) => {
      if (result) {
        this.data = result.data;
      }
    })
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    this.search();
  }

  public onCreate(): void {
    this.isShow = true;
    this.isEdit = false;
  }

  public onEdit(id: string): void {
    this.isShow = true;
    this.isEdit = true;
    this.dataEdit = this.data.find((item) => item.id === id);
  }

  public onDelete(id: string): void {
    const confirmDelete = confirm('Are you sure want to delete this?');
    if (confirmDelete) {
      this.roleService.delete(id).pipe(catchError(() => {
        this.response = 'Role has been set to user, can not delete';
        return of(null);
      })).subscribe((result: boolean) => {
        if (result) {
          this.response = 'Delete Successfully!';
          this.search();
        }
      });
    }
  }

  public onCancelDetail(): void {
    this.isShow = false;
    this.search();
  }




}
