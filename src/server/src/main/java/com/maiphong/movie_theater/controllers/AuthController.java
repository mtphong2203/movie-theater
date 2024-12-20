package com.maiphong.movie_theater.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.maiphong.movie_theater.dtos.auth.LoginRequestDTO;
import com.maiphong.movie_theater.dtos.auth.LoginResponseDTO;
import com.maiphong.movie_theater.dtos.auth.RegisterRequestDTO;
import com.maiphong.movie_theater.services.AuthService;
import com.maiphong.movie_theater.services.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    private final AuthenticationManagerBuilder managerBuilder;

    public AuthController(AuthService authService, TokenService tokenService,
            AuthenticationManagerBuilder managerBuilder) {
        this.authService = authService;
        this.tokenService = tokenService;
        this.managerBuilder = managerBuilder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenService.generateAccessToken(authentication);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccessToken(accessToken);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var result = authService.register(registerRequestDTO);

        return ResponseEntity.ok(result);

    }

}
