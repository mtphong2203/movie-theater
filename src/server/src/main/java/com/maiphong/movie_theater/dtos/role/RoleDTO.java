package com.maiphong.movie_theater.dtos.role;

import com.maiphong.movie_theater.dtos.BaseDTO;

import lombok.*;

@Getter
@Setter
public class RoleDTO extends BaseDTO {
    private String name;

    private String description;

    private boolean isActive;
}
