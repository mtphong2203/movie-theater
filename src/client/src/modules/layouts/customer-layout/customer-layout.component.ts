import { Component } from '@angular/core';
import { HeaderComponent } from "../../common/header/header.component";
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from "../../common/footer/footer.component";

@Component({
  selector: 'app-customer-layout',
  standalone: true,
  imports: [HeaderComponent, RouterOutlet, FooterComponent],
  templateUrl: './customer-layout.component.html',
  styleUrl: './customer-layout.component.css'
})
export class CustomerLayoutComponent {

}
