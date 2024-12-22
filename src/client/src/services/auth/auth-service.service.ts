import { Observable, tap } from "rxjs";
import { IAuthService } from "./auth-service.interface";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class AuthService implements IAuthService {

    private apiUrl: string = 'http://localhost:8080/api/v1/auth';

    constructor(private httpClient: HttpClient) { }

    login(param: string): Observable<any> {
        return this.httpClient.post(`${this.apiUrl}/login`, param).pipe(
            tap((res: any) => {
                const token = res.accessToken;
                if (token != null) {
                    localStorage.setItem('accessToken', token);
                }
            })
        )
    }
    register(param: string): Observable<any> {
        return this.httpClient.post(`${this.apiUrl}/register`, param);
    }

}