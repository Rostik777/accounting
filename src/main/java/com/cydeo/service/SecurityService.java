package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SecurityService extends UserDetailsService {
    UserDTO getLoggedInUser();
}
