package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final CompanyService companyService;

    public UserController(UserService userService, RoleService roleService, CompanyService companyService) {
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) throws Exception{
        model.addAttribute("users", userService.getFilteredUsers());
        return "user/user-list";
    }

    @GetMapping("/create")
    public String navigateToUserCreate(Model model) {
        model.addAttribute("newUser", new UserDTO());
        return "/user/user-create";
    }

    @PostMapping("/create")
    public String createNewUser(@Valid @ModelAttribute("newUser") UserDTO userDTO, BindingResult bindingResult) {
        boolean emailExist = userService.emailExist(userDTO);

        if (bindingResult.hasErrors() || emailExist) {
            if (emailExist) {
                bindingResult.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }
            return "/user/user-create";
        }

        userService.save(userDTO);
        return "redirect:/users/list";
    }

    @GetMapping("/update/{userId}")
    public String navigateToUserUpdate(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.findUserById(userId));
        return "/user/user-update";
    }

    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable("userId") Long userId, @Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult) {
        userDTO.setId(userId);
        boolean emailExist = userService.emailExist(userDTO);

        if (bindingResult.hasErrors() || emailExist) {
           if (emailExist) {
               bindingResult.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
           }
           return "/user/user-update";
        }

        userService.update(userDTO);
        return "redirect:/users/list";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.delete(userId);
        return "redirect:/users/list";
    }

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("companies", companyService.getFilteredCompaniesForCurrentUser());
        model.addAttribute("userRoles", roleService.getFilteredRolesForCurrentUser());
    }
}
