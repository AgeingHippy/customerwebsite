package com.ageinghippy.customerwebsite.controller;

import com.ageinghippy.customerwebsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AppController {

    private final UserService userService;

    @GetMapping(value = {"/","/index", "/homepage"})
    public String viewIndex(Authentication authentication) {
        return "homepage";
    }


//    @GetMapping("/login/success")
//    public String loginSuccess(Authentication authentication) {
//        return "redirect:/homepage";
//    }

}
