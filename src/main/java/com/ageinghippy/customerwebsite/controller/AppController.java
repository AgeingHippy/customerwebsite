package com.ageinghippy.customerwebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping(value = {"/","/index"})
    public String viewHomePage() {
        return "index";
    }

    @GetMapping(value = {"/customer-list"})
    public String viewCustomerList() {
        return "customer-list";
    }
}
