package com.maiphong.movie_theater.dtos.auth;

import lombok.*;

@Getter
@Setter
public class LoginRequestDTO {
    private String username;

    private String password;
}
