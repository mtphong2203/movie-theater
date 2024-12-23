import { BaseDto } from "./base-dto.model";

export class MasterDto extends BaseDto {
    public createdAt: Date;
    public updatedAt: Date;
    public deletedAt: Date;
    public isActive: boolean;

    constructor(id: string, createdAt: Date, updatedAt: Date, deletedAt: Date, isActive: boolean) {
        super(id);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isActive = isActive;
    }
}