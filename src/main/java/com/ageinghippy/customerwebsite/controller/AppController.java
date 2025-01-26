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

    @GetMapping(value = {"/","/index"})
    public String viewHomePage() {
        return "index";
    }

//    @GetMapping(value = {"/customer-list"})
//    public String viewCustomerList() {
//        return "customer-list";
//    }

    @GetMapping("/login/success")
    public String loginSuccess(Authentication authentication) {
        String successPage;
        if (authentication.getAuthorities().contains(userService.getRoleByName("ROLE_ADMIN"))) {
            successPage = "customer-list";
        }
        else {
            successPage = "customer-view";
        }
        return successPage;
    }

}
