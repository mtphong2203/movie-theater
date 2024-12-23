import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { RoleListComponent } from './role/role-list.component';
import { ROLE_SERVICE, USER_SERVICE } from '../../constants/injection.const';
import { RoleService } from '../../services/role/role-service.service';
import { UserListComponent } from './user/user-list.component';
import { UserService } from '../../services/user/user-service.service';

const routes: Routes = [
  {
    path: 'roles',
    component: RoleListComponent
  },
  {
    path: 'users',
    component: UserListComponent
  },
  {
    path: '**',
    redirectTo: 'roles',
    pathMatch: 'full'
  }

]

@NgModule({
  declarations: [],
  providers: [
    {
      provide: ROLE_SERVICE,
      useClass: RoleService
    },
    {
      provide: USER_SERVICE,
      useClass: UserService
    },
  ],
  imports: [
    CommonModule, RouterModule.forChild(routes)
  ]
})
export class ManagementModule { }
