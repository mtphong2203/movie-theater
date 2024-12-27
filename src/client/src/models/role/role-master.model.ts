import { MasterDto } from "../common/master-dto.model";

export class RoleMasterDto extends MasterDto {

    public name: string;
    public description: string;

    constructor(id: string, createdAt: Date, updatedAt: Date, deletedAt: Date, isActive: boolean, name: string, description: string) {
        super(id, createdAt, updatedAt, deletedAt, isActive);
        this.name = name;
        this.description = description;
    }
}