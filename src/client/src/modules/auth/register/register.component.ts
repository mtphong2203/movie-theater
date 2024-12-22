import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IconDefinition } from '@fortawesome/free-brands-svg-icons';
import { faRegistered } from '@fortawesome/free-regular-svg-icons';
import { AUTH_SERVICE } from '../../../constants/injection.const';
import { IAuthService } from '../../../services/auth/auth-service.interface';
import { RegisterReponse } from '../../../models/auth/register.model';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, FontAwesomeModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {

  public form!: FormGroup;
  public faRegister: IconDefinition = faRegistered;

  constructor(@Inject(AUTH_SERVICE) private authService: IAuthService, private router: Router) { }

  ngOnInit(): void {
    this.createForm();
  }

  private createForm(): void {
    this.form = new FormGroup({
      firstName: new FormControl('', Validators.maxLength(50)),
      lastName: new FormControl('', Validators.maxLength(50)),
      username: new FormControl('', Validators.required),
      gender: new FormControl(null, Validators.required),
      phoneNumber: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      email: new FormControl('', [Validators.required, Validators.maxLength(25)]),
      address: new FormControl('', [Validators.maxLength(70)]),
      dateOfBirth: new FormControl(null),
      password: new FormControl('', Validators.required),
      confirmPassword: new FormControl('', Validators.required),
    });
  }

  public onSubmit(): void {
    const data = this.form.value;
    this.authService.register(data).subscribe((result: RegisterReponse) => {
      if (result) {
        this.router.navigate(['/auth/login']);
      }
    });
  }
}
