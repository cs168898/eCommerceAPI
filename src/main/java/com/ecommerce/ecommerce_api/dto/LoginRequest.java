package com.ecommerce.ecommerce_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private char[] password;

    // Getters and setters
}
