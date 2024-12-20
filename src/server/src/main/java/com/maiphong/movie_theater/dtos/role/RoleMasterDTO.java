package com.maiphong.movie_theater.dtos.role;

import com.maiphong.movie_theater.dtos.MasterDTO;

import lombok.*;

@Getter
@Setter
public class RoleMasterDTO extends MasterDTO {
    private String name;

    private String description;
}
