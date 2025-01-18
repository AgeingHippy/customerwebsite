package com.ageinghippy.customerwebsite.model;

import lombok.Getter;

@Getter
public class CustomerError {
    public String errorMessage;

    CustomerError(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
