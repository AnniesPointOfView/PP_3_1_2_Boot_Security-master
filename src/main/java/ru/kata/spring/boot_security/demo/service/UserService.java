package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.User;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAllUsers();
    User findById(int id);
    User findByUsername(String username);
    User findUser(int id);
    boolean saveUser(User user, BindingResult bindingResult, Model model);
    boolean saveUser(User user);
    void deleteUser(int id);
}
