package com.ageinghippy.customerwebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Builder
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @Size(min=3)
    private String fullName;
    @Email
    private String emailAddress;
    @NotNull
    @Min(value = 18)
    private Integer age;
    @NotEmpty
    private String address;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Car car;
}
