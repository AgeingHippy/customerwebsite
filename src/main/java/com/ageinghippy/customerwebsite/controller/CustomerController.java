package com.ageinghippy.customerwebsite.controller;

import com.ageinghippy.customerwebsite.model.Car;
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

    @GetMapping(value = {"/view"})
    public String showViewCustomerPage(Model model) {
        final List<Customer> customerList = customerService.getAllCustomers();

        model.addAttribute("customerList", customerList);

        return "customer-view";
    }

    @GetMapping(value = {"/list"})
    public String showCustomerListPage(Model model) {
        final List<Customer> customerList = customerService.getAllCustomers();

        model.addAttribute("customerList", customerList);

        return "customer-list";
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

        return "redirect:/customer/list";
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

        return "redirect:/customer/list";
    }

    @PostMapping("/assign/{id}")
    public String assignCar(@PathVariable(name = "id") Long id,
                            @ModelAttribute("car") Car car) {
        customerService.assignCarToCustomer(id, car.getId());

        return "redirect:/customer/list";
    }

    @GetMapping("/remove/{id}")
    public String removeCar(@PathVariable(name = "id") Long id) {
        customerService.removeCarFromCustomer(id);

        return "redirect:/customer/list";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer/list";
    }

}
