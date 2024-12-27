import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { AUTH_SERVICE } from '../constants/injection.const';
import { AuthService } from '../services/auth/auth-service.service';

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes),
  provideHttpClient(withFetch()),
  {
    provide: AUTH_SERVICE,
    useClass: AuthService
  }
  ]
};
