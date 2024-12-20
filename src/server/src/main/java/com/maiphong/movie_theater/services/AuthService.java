package com.maiphong.movie_theater.services;

import java.util.UUID;

import com.maiphong.movie_theater.dtos.auth.RegisterRequestDTO;

public interface AuthService {
    UUID register(RegisterRequestDTO registerDTO);
}
