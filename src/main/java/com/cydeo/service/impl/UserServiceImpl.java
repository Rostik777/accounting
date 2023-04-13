package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import com.cydeo.utils.MapperUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public UserDTO findUserById(Long id) {
        return mapperUtil.convert(userRepository.findUserById(id), new UserDTO());
    }
}
