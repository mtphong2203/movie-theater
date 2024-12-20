package com.maiphong.movie_theater.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.maiphong.movie_theater.dtos.role.RoleCreateUpdateDTO;
import com.maiphong.movie_theater.dtos.role.RoleMasterDTO;
import com.maiphong.movie_theater.entities.Role;
import com.maiphong.movie_theater.exceptions.ResourceNotFoundException;
import com.maiphong.movie_theater.mappers.RoleMapper;
import com.maiphong.movie_theater.repositories.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleMasterDTO> getAll() {
        var roles = roleRepository.findAll();

        var roleMasters = roles.stream().map(role -> {
            RoleMasterDTO roleMaster = roleMapper.toMasterDTO(role);
            return roleMaster;
        }).toList();

        return roleMasters;
    }

    @Override
    public RoleMasterDTO getById(UUID id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not found");
        }
        RoleMasterDTO roleMaster = roleMapper.toMasterDTO(role);
        return roleMaster;
    }

    @Override
    public List<RoleMasterDTO> searchByName(String keyword) {
        Specification<Role> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };

        List<Role> roles = roleRepository.findAll(spec);

        List<RoleMasterDTO> roleMasters = roles.stream().map(role -> {
            var roleMaster = roleMapper.toMasterDTO(role);
            return roleMaster;
        }).toList();

        return roleMasters;
    }

    @Override
    public Page<RoleMasterDTO> searchPaginated(String keyword, Pageable pageable) {
        Specification<Role> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };

        Page<Role> roles = roleRepository.findAll(spec, pageable);

        Page<RoleMasterDTO> roleMasters = roles.map(role -> {
            var roleMaster = roleMapper.toMasterDTO(role);
            return roleMaster;
        });

        return roleMasters;
    }

    @Override
    public RoleMasterDTO create(RoleCreateUpdateDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role create is required");
        }

        Role role = roleRepository.findByName(roleDTO.getName());

        if (role != null) {
            throw new IllegalArgumentException("Role name is already exist!");
        }

        Role newRole = roleMapper.toEntity(roleDTO);

        newRole = roleRepository.save(newRole);

        RoleMasterDTO roleMaster = roleMapper.toMasterDTO(newRole);

        return roleMaster;
    }

    @Override
    public RoleMasterDTO update(UUID id, RoleCreateUpdateDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role create is required");
        }

        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not exist!");
        }

        role = roleMapper.toEntity(roleDTO, role);
        role.setUpdatedAt(LocalDateTime.now());

        role = roleRepository.save(role);

        RoleMasterDTO roleMaster = roleMapper.toMasterDTO(role);

        return roleMaster;
    }

    @Override
    public boolean delete(UUID id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not exist!");
        }

        roleRepository.delete(role);

        return !roleRepository.existsById(id);
    }

}
