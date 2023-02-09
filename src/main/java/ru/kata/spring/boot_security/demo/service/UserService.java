package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();
    User getUserById(long id);
    void saveUser(User user);
    void updateUser(User updateUser);
    void deleteUserById(long id);
    User findByEmail(String email);
    public UserDetails loadUserByUsername(String email);

    List<Role> getAllRoles();


}
