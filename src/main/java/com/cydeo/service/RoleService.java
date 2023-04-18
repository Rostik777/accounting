package com.cydeo.service;

import com.cydeo.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO findRoleById(Long id);
    List<RoleDTO> getFilteredRolesForCurrentUser();
}
