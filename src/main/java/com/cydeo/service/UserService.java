package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findUserById(Long id);
    List<UserDTO> getFilteredUsers() throws Exception;
    UserDTO findByUsername(String username);
    boolean emailExist(UserDTO userDTO);
    UserDTO save(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
}
