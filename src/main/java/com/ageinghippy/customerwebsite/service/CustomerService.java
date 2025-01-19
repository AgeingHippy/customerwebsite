package com.ageinghippy.customerwebsite.service;

import com.ageinghippy.customerwebsite.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer saveCustomer(Customer customer);

    Customer assignCarToCustomer(Long id, Long carId);

    Customer removeCarFromCustomer(Long id);

    Customer getCustomer(Long id);

    void deleteCustomer(Long id);

    List<Customer> saveAllCustomer(List<Customer> customerList);
}
