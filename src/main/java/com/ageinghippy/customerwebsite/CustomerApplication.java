package com.ageinghippy.customerwebsite;

import com.ageinghippy.customerwebsite.model.Customer;
import com.ageinghippy.customerwebsite.model.Role;
import com.ageinghippy.customerwebsite.model.User;
import com.ageinghippy.customerwebsite.repository.RoleRepository;
import com.ageinghippy.customerwebsite.repository.UserRepository;
import com.ageinghippy.customerwebsite.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CustomerApplication implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // The main method is defined here which will start your application.
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class);
    }

    // You can also define a run method which performs an operation
    // at runtime. In this example, the run method saves some Customer
    // data into the database for testing.
    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findAll().isEmpty()) {
            roleRepository.saveAll(List.of(
                    Role.builder().name("ROLE_USER").build(),
                    Role.builder().name("ROLE_ADMIN").build()
            ));
        }

        if (userRepository.findAll().isEmpty()) {
            userRepository.save(
                    User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("password"))
                            .roles(List.of(roleRepository.findByName("ROLE_ADMIN")))
                            .build()
            );
        }

        if (customerService.getAllCustomers().isEmpty()) {
            //create customers
            customerService.saveAllCustomer(Arrays.asList(
                            Customer.builder()
                                    .fullName("Customer 1")
                                    .emailAddress("customer1@gmail.com")
                                    .address("Customer Address One")
                                    .age(30)
                                    .build(),
                            Customer.builder()
                                    .fullName("Customer 2")
                                    .emailAddress("customer2@gmail.com")
                                    .address("Customer Address Two")
                                    .age(28)
                                    .build(),
                            Customer.builder()
                                    .fullName("Customer 3")
                                    .emailAddress("customer3@gmail.com")
                                    .address("Customer Address Three")
                                    .age(32)
                                    .build()
                    )
            );
        }
    }

}
