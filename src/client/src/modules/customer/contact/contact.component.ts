import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFacebook, faTwitter, faYoutube, IconDefinition } from '@fortawesome/free-brands-svg-icons';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent implements OnInit {

  public contactForm!: FormGroup;

  public response: string = '';

  public faFacebook: IconDefinition = faFacebook;
  public faYoutube: IconDefinition = faYoutube;
  public faTwitter: IconDefinition = faTwitter;

  ngOnInit(): void {
    this.createForm();
  }

  private createForm(): void {
    this.contactForm = new FormGroup({
      email: new FormControl('', Validators.required),
      message: new FormControl('', Validators.maxLength(50)),
    });
  }

  public onSubmit(): void {
    if (this.contactForm.valid) {
      this.response = 'Thank you for contact us!';
      this.contactForm.reset();
    }
  }
}
