import { HttpClient } from "@angular/common/http";
import { MasterService } from "../master-service.service";
import { IUserService } from "./user-service.interface";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class UserService extends MasterService implements IUserService {
    constructor(protected override httpClient: HttpClient) {
        super('users', httpClient);
    }
}