package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import com.cydeo.service.SecurityService;
import com.cydeo.utils.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public RoleDTO findRoleById(Long id) {
        return mapperUtil.convert(roleRepository.findRoleById(id), new RoleDTO());
    }

    @Override
    public List<RoleDTO> getFilteredRolesForCurrentUser() {
        UserDTO user = securityService.getLoggedInUser();
        if (user.getRole().getDescription().equals("RootUser")) {
            List<RoleDTO> list = new ArrayList<>();
            list.add(mapperUtil.convert(roleRepository.findByDescription("Admin"), new RoleDTO()));
            return list;
        } else {
            return roleRepository.findAll()
                    .stream()
                    .filter(role -> !role.getDescription().equals("Root User"))
                    .map(role -> mapperUtil.convert(role, new RoleDTO()))
                    .collect(Collectors.toList());
        }
    }
}
