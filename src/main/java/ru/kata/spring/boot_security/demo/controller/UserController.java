package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.AppService;

import java.security.Principal;

@Controller
@RequestMapping()
public class UserController {

    private final AppService appService;

    @Autowired
    public UserController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String showUserPage(Model model, Principal principal) {
        model.addAttribute("user",
                appService.findByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

}