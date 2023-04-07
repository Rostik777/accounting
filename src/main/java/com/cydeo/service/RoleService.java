package com.cydeo.service;

import com.cydeo.dto.RoleDTO;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    RoleDTO findRoleById(Long id);
}
