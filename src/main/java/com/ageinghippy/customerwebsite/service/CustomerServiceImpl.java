package com.ageinghippy.customerwebsite.service;

import com.ageinghippy.customerwebsite.model.Customer;
import com.ageinghippy.customerwebsite.repository.CustomerRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CarService carService;
    private final CustomerRepository customerRepository;
    private final Validator validator;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        validateCustomer(customer);
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer assignCarToCustomer(Long id, Long carId) {
        Customer customer = customerRepository.findById(id).orElse(null);
        customer.setCar(carService.getCar(carId));
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer removeCarFromCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        customer.setCar(null);
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        //todo - determine correct CascadeType so that manual removal of car is not necessary - NOT WORKING!!
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer.getCar() != null) {
            customer.setCar(null);
            customerRepository.saveAndFlush(customer);
            customer = customerRepository.findById(id).orElse(null);
        }
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Customer> saveAllCustomer(List<Customer> customerList) {
        return customerRepository.saveAll(customerList);
    }

    private void validateCustomer(Customer customer) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

