import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IconDefinition } from '@fortawesome/free-brands-svg-icons';
import { faUser } from '@fortawesome/free-regular-svg-icons';
import { AUTH_SERVICE } from '../../../constants/injection.const';
import { IAuthService } from '../../../services/auth/auth-service.interface';
import { LoginResponse } from '../../../models/auth/login.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, FontAwesomeModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  public form!: FormGroup;

  public faUser: IconDefinition = faUser;

  constructor(@Inject(AUTH_SERVICE) private authService: IAuthService, private router: Router) { }


  ngOnInit(): void {
    this.createForm();
  }

  private createForm(): void {
    this.form = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  public onSubmit(): void {
    const data = this.form.value;
    this.authService.login(data).subscribe((result: LoginResponse) => {
      if (result) {
        this.router.navigate(['/']);
      }
    })
  }

}
