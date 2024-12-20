package com.maiphong.movie_theater.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class MasterCreateUpdateDTO {
    @NotNull(message = "Active is required")
    private boolean isActive;
}
