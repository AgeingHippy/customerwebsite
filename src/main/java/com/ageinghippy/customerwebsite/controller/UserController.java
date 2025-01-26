package com.ageinghippy.customerwebsite.controller;

import com.ageinghippy.customerwebsite.model.User;
import com.ageinghippy.customerwebsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute User user ) {
        user = userService.createUser(user);

        return "redirect :/login";
    }
}
