package com.maiphong.movie_theater.dtos.role;

import org.hibernate.validator.constraints.Length;

import com.maiphong.movie_theater.dtos.MasterCreateUpdateDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class RoleCreateUpdateDTO extends MasterCreateUpdateDTO {
    @NotNull(message = "Name is required")
    private String name;

    @Length(max = 500, message = "Maximum is 500 characters")
    private String description;
}
