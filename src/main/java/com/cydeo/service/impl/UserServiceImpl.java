package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import com.cydeo.utils.MapperUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, @Lazy SecurityService securityService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public UserDTO findUserById(Long id) {
        return mapperUtil.convert(userRepository.findUserById(id), new UserDTO());
    }

    @Override
    public List<UserDTO> getFilteredUsers() {
        List<User> userList;
        if (isCurrentUserRootUser()) {
            userList = userRepository.findAllByRoleDescription("Admin");
        } else {
            userList = userRepository.findAllByCompanyTitle(getCurrentUserCompanyTitle());
        }

        return userList.stream()
                .sorted(Comparator.comparing((User user) -> user.getCompany().getTitle()).thenComparing(user -> user.getRole().getDescription()))
                .map(entity -> {
                    UserDTO dto = mapperUtil.convert(entity, new UserDTO());
                    dto.setIsOnlyAdmin(checkIfOnlyAdminForCompany(dto));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return mapperUtil.convert(user, new UserDTO());
    }

    private Boolean isCurrentUserRootUser() {
        return  securityService.getLoggedInUser().getRole().getDescription().equalsIgnoreCase("root user");
    }

    private String getCurrentUserCompanyTitle() {
        String currentUsername = securityService.getLoggedInUser().getUsername();
        return userRepository.findByUsername(currentUsername).getCompany().getTitle();
    }

    private Boolean checkIfOnlyAdminForCompany(UserDTO dto) {
        if(dto.getRole().getDescription().equalsIgnoreCase("Admin")) {
            List<User> userList = userRepository.findAllByCompanyTitleAndRoleDescription(dto.getCompany().getTitle(), "Admin");
            return userList.size() == 1;
        }
        return false;
    }
}
