package ru.kata.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class InitData {
    private final UserService userService;
    private final RoleService roleService;

    public InitData(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void init() {

        // roles
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        roleService.createRole(adminRole);
        roleService.createRole(userRole);

        // users
        User adminUser = new User("Phill",
                "Collins",
                "ph.collins@ggmail.com",
                "admin",
                "admin",
                adminRole);

        User user = new User("Adam",
                "Robbins",
                "a.robbins@ggmail.com",
                "user",
                "user",
                userRole);

        userService.saveUser(adminUser);
        userService.saveUser(user);
    }
}
