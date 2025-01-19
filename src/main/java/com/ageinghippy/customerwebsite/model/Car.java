package com.ageinghippy.customerwebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(mappedBy = "car")
    private Customer customer;

}
