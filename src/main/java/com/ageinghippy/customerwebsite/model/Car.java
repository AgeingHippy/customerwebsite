package com.ageinghippy.customerwebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
public class Car {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String make;

    @NotEmpty
    @Column(nullable = false)
    private String model;

    @NotEmpty
    @Column(unique = true,nullable = false)
    private String registration;

    @Column(nullable = false)
    private Integer costPerDay;

    @Column(name = "customer_id")
    private Customer customer;
}
