package com.ecommerce.ecommerce_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
    private String username;
    private String email;
    private String password;

    // convert the string password to a char array and return it
    public char[] getPasswordChars() {
        return password != null ? password.toCharArray() : new char[0];
    }

}

