package com.ageinghippy.customerwebsite.controller;

import com.ageinghippy.customerwebsite.model.Customer;
import com.ageinghippy.customerwebsite.model.CustomerErrorResponse;
import com.ageinghippy.customerwebsite.service.CustomerService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = {"","/"})
    public String showViewCustomerPage(Model model) {

        // call the service to retrieve all customers
        final List<Customer> customerList = customerService.getAllCustomers();

        // once the customers are retrieved, you can
        // store them in model and return the view
        model.addAttribute("customerList", customerList);

        return "view-customer";
    }

    @GetMapping("/new")
    public String showNewCustomerPage(Model model) {

        // a new (empty) Customer is created and added to the model
        Customer customer = new Customer();
        model.addAttribute("customer", customer);

        // return the "new-customer" view
        return "new-customer";
    }

    @PostMapping(value = "/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer, Model model) {
        try {
            customerService.saveCustomer(customer);
        } catch (ConstraintViolationException e) {
            CustomerErrorResponse customerErrorResponse = new CustomerErrorResponse(e.getConstraintViolations());
            model.addAttribute("customerErrorResponse", customerErrorResponse);
            return "error-page";
        }

        return "redirect:/customer/";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditCustomerPage(@PathVariable(name = "id") Long id) {

        ModelAndView mav = new ModelAndView("edit-customer");
        Customer customer = customerService.getCustomer(id);
        mav.addObject("customer", customer);
        return mav;
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable(name = "id") Long id,
                                 @ModelAttribute("customer") Customer customer,
                                 Model model) {

        if (!id.equals(customer.getId())) {
            model.addAttribute("message",
                    "Cannot update, customer id " + customer.getId()
                            + " doesn't match id to update: " + id + ".");
            return "error-page";
        }

        try {
            customerService.saveCustomer(customer);
        } catch (ConstraintViolationException e) {
            CustomerErrorResponse customerErrorResponse = new CustomerErrorResponse(e.getConstraintViolations());
            model.addAttribute("customerErrorResponse", customerErrorResponse);
            return "error-page";
        }

        return "redirect:/customer/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer/";
    }

}
