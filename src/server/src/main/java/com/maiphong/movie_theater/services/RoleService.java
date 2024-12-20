package com.maiphong.movie_theater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.movie_theater.dtos.role.RoleCreateUpdateDTO;
import com.maiphong.movie_theater.dtos.role.RoleMasterDTO;

public interface RoleService {
    List<RoleMasterDTO> getAll();

    RoleMasterDTO getById(UUID id);

    List<RoleMasterDTO> searchByName(String keyword);

    Page<RoleMasterDTO> searchPaginated(String keyword, Pageable pageable);

    RoleMasterDTO create(RoleCreateUpdateDTO roleDTO);

    RoleMasterDTO update(UUID id, RoleCreateUpdateDTO roleDTO);

    boolean delete(UUID id);
}
