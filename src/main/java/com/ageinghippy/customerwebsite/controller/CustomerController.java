package com.ageinghippy.customerwebsite.controller;

import com.ageinghippy.customerwebsite.model.Customer;
import com.ageinghippy.customerwebsite.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/")
    public String viewHomePage(Model model) {

        // call the service to retrieve all customers
        final List<Customer> customerList = customerService.getAllCustomers();

        // once the customers are retrieved, you can
        // store them in model and return the view
        model.addAttribute("customerList", customerList);

        return "index";
    }

}
