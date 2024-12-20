package com.maiphong.movie_theater.dtos;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
public class MasterDTO extends BaseDTO {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private boolean isActive;
}
