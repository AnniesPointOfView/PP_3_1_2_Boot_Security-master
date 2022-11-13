package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@Transactional
public class AppServiceImpl implements AppService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName", "lastName"));
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUser(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean saveUser(User user, BindingResult bindingResult, Model model) {
        model.addAttribute("allRoles", findAllRoles());

        if (bindingResult.hasErrors()) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userRepository.save(user);
        } catch (PersistenceException e) {
            model.addAttribute("persistenceException", true);
            return false;
        }

        return true;
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        User user = findById(id);
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());

        user.getRoles().clear();
        updatedUser.getRoles().forEach(role ->
                user.getRoles().add(roleRepository.findByName(role.getName())));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.delete(findById(id));
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

}
