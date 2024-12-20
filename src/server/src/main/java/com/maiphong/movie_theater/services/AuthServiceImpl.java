package com.maiphong.movie_theater.services;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maiphong.movie_theater.dtos.auth.RegisterRequestDTO;
import com.maiphong.movie_theater.entities.User;
import com.maiphong.movie_theater.mappers.UserMapper;
import com.maiphong.movie_theater.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(auth -> "ROLE_" + auth.getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);

    }

    @Override
    public UUID register(RegisterRequestDTO registerDTO) {
        if (registerDTO == null) {
            throw new IllegalArgumentException("Register is required");
        }

        User user = userRepository.findByUsername(registerDTO.getUsername());

        if (user != null) {
            throw new IllegalArgumentException("User name is already exist!");
        }

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password is not match");
        }

        user = userMapper.toEntity(registerDTO);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user = userRepository.save(user);

        return user.getId();
    }

}
