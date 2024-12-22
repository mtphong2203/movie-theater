import { InjectionToken } from "@angular/core";
import { IRoleService } from "../services/role/role-service.interface";
import { IAuthService } from "../services/auth/auth-service.interface";

export const ROLE_SERVICE = new InjectionToken<IRoleService>('ROLE_SERVICE');
export const AUTH_SERVICE = new InjectionToken<IAuthService>('AUTH_SERVICE');