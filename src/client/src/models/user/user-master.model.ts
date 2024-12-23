import { MasterDto } from "../common/master-dto.model";

export class UserMasterDto extends MasterDto {

    public firstName: string;
    public lastName: string;
    public username: string;
    public gender: boolean;
    public dateOfBirth: Date;
    public email: string;
    public phoneNumber: string;
    public address: string;

    constructor(id: string, createdAt: Date, updatedAt: Date, deletedAt: Date, isActive: boolean,
        firstName: string, lastName: string, username: string, gender: boolean, dateOfBirth: Date, email: string, phoneNumber: string, address: string,
    ) {
        super(id, createdAt, updatedAt, deletedAt, isActive);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}