package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAdminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin_page";
    }

    @GetMapping("/new")
    public String getUserCreateForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getRoles());
        return "new_user";
    }

    @PostMapping("/createNew")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "my_roles") String stringRole) {
        Role role = new Role(stringRole);
        roleService.saveRole(role);
        user.setRoles(Set.of(role));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/{id}/edit")
    public String getUserEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "edit_user";
    }

    @PatchMapping(value = "/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        user.setPassword(user.getPassword());
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String removeUserById(@PathVariable("id") Long id) {
        roleService.removeRoleById(id);
        userService.removeUserById(id);
        return "redirect:/admin";
    }

}
