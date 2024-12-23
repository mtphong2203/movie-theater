import { Component, Inject, OnChanges, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MasterDetailsComponent } from '../../master-details/master-details.component';
import { UserMasterDto } from '../../../../models/user/user-master.model';
import { USER_SERVICE } from '../../../../constants/injection.const';
import { IUserService } from '../../../../services/user/user-service.interface';

@Component({
  selector: 'app-user-details',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule],
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.css'
})
export class UserDetailsComponent extends MasterDetailsComponent<UserMasterDto> implements OnChanges {

  constructor(@Inject(USER_SERVICE) private userService: IUserService) { super(); }

  ngOnChanges(changes: SimpleChanges): void {
    this.createForm();
    this.patchValue();
  }

  private createForm(): void {
    this.form = new FormGroup({
      firstName: new FormControl('', Validators.maxLength(50)),
      lastName: new FormControl('', Validators.maxLength(50)),
      username: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      gender: new FormControl(null, Validators.required),
      dateOfBirth: new FormControl(null, Validators.required),
      email: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      phoneNumber: new FormControl('', [Validators.required, Validators.maxLength(25)]),
      address: new FormControl('', Validators.maxLength(50)),
      password: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      confirmPassword: new FormControl('', Validators.maxLength(50)),
      active: new FormControl(false),
    });
  }

  private patchValue(): void {
    if (this.isEdit && this.dataEdit != null) {
      this.form.patchValue(this.dataEdit);
    }
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    const data = this.form.value;
    if (this.isEdit && this.dataEdit != null) {
      this.userService.update(this.dataEdit.id, data).subscribe((result: UserMasterDto) => {
        if (result) {
          this.cancel.emit();
        }
      });
    } else {
      this.userService.create(data).subscribe((result: UserMasterDto) => {
        if (result) {
          this.cancel.emit();
        }
      });
    }
  }

  public onCancel(): void {
    this.cancel.emit();
  }



}
