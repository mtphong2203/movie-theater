package com.maiphong.movie_theater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.movie_theater.dtos.role.RoleCreateUpdateDTO;
import com.maiphong.movie_theater.dtos.role.RoleMasterDTO;
import com.maiphong.movie_theater.entities.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toEntity(RoleCreateUpdateDTO DTO);

    Role toEntity(RoleCreateUpdateDTO DTO, @MappingTarget Role role);

    RoleMasterDTO toMasterDTO(Role role);
}
