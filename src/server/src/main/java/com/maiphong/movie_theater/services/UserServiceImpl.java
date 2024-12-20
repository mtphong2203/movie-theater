package com.maiphong.movie_theater.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.maiphong.movie_theater.dtos.role.RoleDTO;
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

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserMasterDTO> getAll() {
        var users = userRepository.findAll();

        var userMasters = users.stream().map(user -> {
            UserMasterDTO userMaster = userMapper.toMasterDTO(user);
            if (user.getRole() != null) {
                // Role dto set to master user
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(user.getRole().getId());
                roleDTO.setName(user.getRole().getName());
                roleDTO.setDescription(user.getRole().getDescription());
                roleDTO.setActive(user.getRole().isActive());

                userMaster.setRoleDTO(roleDTO);
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
        if (user.getRole() != null) {
            // Role dto set to master user
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(user.getRole().getId());
            roleDTO.setName(user.getRole().getName());
            roleDTO.setDescription(user.getRole().getDescription());
            roleDTO.setActive(user.getRole().isActive());

            userMaster.setRoleDTO(roleDTO);
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
            if (user.getRole() != null) {
                // Role dto set to master user
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(user.getRole().getId());
                roleDTO.setName(user.getRole().getName());
                roleDTO.setDescription(user.getRole().getDescription());
                roleDTO.setActive(user.getRole().isActive());

                userMaster.setRoleDTO(roleDTO);
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
            if (user.getRole() != null) {
                // Role dto set to master user
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(user.getRole().getId());
                roleDTO.setName(user.getRole().getName());
                roleDTO.setDescription(user.getRole().getDescription());
                roleDTO.setActive(user.getRole().isActive());

                userMaster.setRoleDTO(roleDTO);
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
        // Check if user has role
        if (userDTO.getRoleId() != null) {
            var role = roleRepository.findById(userDTO.getRoleId()).orElse(null);
            if (role != null) {
                newUser.setRole(role);
            }
        }

        newUser = userRepository.save(newUser);

        UserMasterDTO userMaster = userMapper.toMasterDTO(newUser);
        // Role dto set to master user
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(newUser.getRole().getId());
        roleDTO.setName(newUser.getRole().getName());
        roleDTO.setDescription(newUser.getRole().getDescription());
        roleDTO.setActive(newUser.getRole().isActive());

        userMaster.setRoleDTO(roleDTO);

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
        user.setUpdatedAt(LocalDateTime.now());
        // Check if user has role
        if (userDTO.getRoleId() != null) {
            var role = roleRepository.findById(userDTO.getRoleId()).orElse(null);
            if (role != null) {
                user.setRole(role);
            }
        }

        user = userRepository.save(user);

        UserMasterDTO userMaster = userMapper.toMasterDTO(user);

        // Role dto set to master user
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(user.getRole().getId());
        roleDTO.setName(user.getRole().getName());
        roleDTO.setDescription(user.getRole().getDescription());
        roleDTO.setActive(user.getRole().isActive());

        userMaster.setRoleDTO(roleDTO);

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
