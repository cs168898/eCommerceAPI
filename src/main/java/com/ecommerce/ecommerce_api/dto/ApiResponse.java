package com.ecommerce.ecommerce_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// T is a placeholder name , representing the word 'Type' , which can be any type
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // constructor with data returned
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}

