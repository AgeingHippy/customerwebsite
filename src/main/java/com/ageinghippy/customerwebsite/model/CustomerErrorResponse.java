package com.ageinghippy.customerwebsite.model;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class CustomerErrorResponse {
    private List<CustomerError> errors;

    public CustomerErrorResponse(Set<ConstraintViolation<?>> constraintViolations) {
        this.errors = new ArrayList<>();
        constraintViolations.forEach(violation -> {
            errors.add(new CustomerError(
                    violation.getPropertyPath().toString() + " - " + violation.getMessage()));
        });
    }

    public CustomerErrorResponse(String errorMessage) {
        this.errors = new ArrayList<>();
        errors.add(new CustomerError(errorMessage));
    }
}
