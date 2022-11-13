package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface AppService extends UserDetailsService {

    List<User> findAllUsers();

    User findById(int id);

    User findByUsername(String username);

    User findUser(int id);

    boolean saveUser(User user, BindingResult bindingResult, Model model);

    void updateUser(int id, User updatedUser);

    void deleteUser(int id);

    List<Role> findAllRoles();

}
