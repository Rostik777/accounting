package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    User findByUsername(String username);
    List<User> findAllByRoleDescription(String roleDescription);
    List<User> findAllByCompanyTitle(String companyName);
    List<User> findAllByCompanyTitleAndRoleDescription(String companyName, String role);

}
