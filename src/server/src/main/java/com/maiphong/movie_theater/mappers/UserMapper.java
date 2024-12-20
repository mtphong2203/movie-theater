package com.maiphong.movie_theater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.movie_theater.dtos.auth.RegisterRequestDTO;
import com.maiphong.movie_theater.dtos.user.UserCreateUpdateDTO;
import com.maiphong.movie_theater.dtos.user.UserMasterDTO;
import com.maiphong.movie_theater.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateUpdateDTO DTO);

    User toEntity(RegisterRequestDTO registerDTO);

    User toEntity(UserCreateUpdateDTO DTO, @MappingTarget User user);

    UserMasterDTO toMasterDTO(User user);

}
