package com.maiphong.movie_theater.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maiphong.movie_theater.dtos.user.UserCreateUpdateDTO;
import com.maiphong.movie_theater.dtos.user.UserMasterDTO;
import com.maiphong.movie_theater.entities.User;
import com.maiphong.movie_theater.exceptions.ResourceNotFoundException;
import com.maiphong.movie_theater.mappers.UserMapper;
import com.maiphong.movie_theater.repositories.RoleRepository;
import com.maiphong.movie_theater.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserMasterDTO> getAll() {
        var users = userRepository.findAll();

        var userMasters = users.stream().map(user -> {
            UserMasterDTO userMaster = userMapper.toMasterDTO(user);
            if (user.getRoles() != null) {
                userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
            }
            return userMaster;
        }).toList();

        return userMasters;
    }

    @Override
    public UserMasterDTO getById(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not found");
        }
        UserMasterDTO userMaster = userMapper.toMasterDTO(user);
        if (user.getRoles() != null) {
            userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
        }

        return userMaster;
    }

    @Override
    public List<UserMasterDTO> searchByName(String keyword) {
        Specification<User> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(cb.like(cb.lower(root.get("username")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("email")), "%" + keyword.toLowerCase() + "%"));
        };

        List<User> users = userRepository.findAll(spec);

        List<UserMasterDTO> userMasters = users.stream().map(user -> {
            var userMaster = userMapper.toMasterDTO(user);
            if (user.getRoles() != null) {
                userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
            }
            return userMaster;
        }).toList();

        return userMasters;
    }

    @Override
    public Page<UserMasterDTO> searchPaginated(String keyword, Pageable pageable) {
        Specification<User> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(cb.like(cb.lower(root.get("username")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("email")), "%" + keyword.toLowerCase() + "%"));
        };

        Page<User> users = userRepository.findAll(spec, pageable);

        Page<UserMasterDTO> userMasters = users.map(user -> {
            var userMaster = userMapper.toMasterDTO(user);
            if (user.getRoles() != null) {
                userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
            }
            return userMaster;
        });

        return userMasters;
    }

    @Override
    public UserMasterDTO create(UserCreateUpdateDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User create is required");
        }

        User user = userRepository.findByUsername(userDTO.getUsername());

        if (user != null) {
            throw new IllegalArgumentException("User name is already exist!");
        }

        User newUser = userMapper.toEntity(userDTO);
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // Check if user has role
        if (userDTO.getRoleId() != null) {
            var role = roleRepository.findById(userDTO.getRoleId());
            if (role != null) {
                newUser.setRoles(Collections.singleton(role.get()));
            }
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password is not match");
        }

        newUser = userRepository.save(newUser);

        UserMasterDTO userMaster = userMapper.toMasterDTO(newUser);
        if (userDTO.getRoleId() != null) {
            userMaster.setRole(newUser.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
        }

        return userMaster;
    }

    @Override
    public UserMasterDTO update(UUID id, UserCreateUpdateDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User create is required");
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }

        user = userMapper.toEntity(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        // Check if user has role
        if (userDTO.getRoleId() != null) {
            var role = roleRepository.findById(userDTO.getRoleId());
            if (role != null) {
                user.setRoles(Collections.singleton(role.get()));
            }
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password is not match");
        }

        user = userRepository.save(user);

        UserMasterDTO userMaster = userMapper.toMasterDTO(user);
        if (userDTO.getRoleId() != null) {
            userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
        }

        return userMaster;
    }

    @Override
    public boolean delete(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }

        userRepository.delete(user);

        return !userRepository.existsById(id);
    }

}
