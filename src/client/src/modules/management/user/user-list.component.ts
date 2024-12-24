import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { USER_SERVICE } from '../../../constants/injection.const';
import { IUserService } from '../../../services/user/user-service.interface';
import { TableComponent } from "../../../core/components/table/table.component";
import { UserDetailsComponent } from "./user-details/user-details.component";
import { MasterListComponent } from '../master-list/master-list.component';
import { UserMasterDto } from '../../../models/user/user-master.model';
import { Column } from '../../../models/common/column.model';
import { ResponseData } from '../../../models/response-data.model';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule, CommonModule, TableComponent, UserDetailsComponent],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent extends MasterListComponent<UserMasterDto> implements OnInit {

  public columns: Column[] = [
    { name: 'username', title: 'UserName' },
    { name: 'gender', title: 'Gender' },
    { name: 'dateOfBirth', title: 'Birth' },
    { name: 'email', title: 'Email' },
    { name: 'phoneNumber', title: 'Phone' },
    { name: 'address', title: 'Address' },
    { name: 'active', title: 'Active' },
    { name: 'role', title: 'Role' },
  ];

  constructor(@Inject(USER_SERVICE) private userService: IUserService) {
    super();
  }

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
      page: this.currentPage,
      size: this.currentPageSize
    }
    this.userService.search(param).subscribe((result: ResponseData<UserMasterDto>) => {
      if (result) {
        this.data = result.data;
        this.pageInfo = result.page;
      }
    });
  }

  public onSubmit(): void {
    if (this.form.valid) {
      this.search();
    }
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
    const confirmDelete = confirm('Are you sure want to delete?');
    if (confirmDelete) {
      this.userService.delete(id).subscribe((result: boolean) => {
        if (result) {
          this.response = 'User delete successfully!';
          this.search();
        }
      });
    }
  }

  public onCancelDetail(): void {
    this.isShow = false;
    this.search();
  }

  public getPageList(): number[] {
    const start = Math.max(0, this.pageInfo.number - this.pageLimit);
    const end = Math.min(this.pageInfo.totalPages - 1, this.pageInfo.number + this.pageLimit);
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  }

  public onChangeNumber(item: number): void {
    this.currentPage = item;
    this.search();
  }

  public onChangeSize(size: any): void {
    this.currentPageSize = size.target.value;
    this.search();
  }




}
