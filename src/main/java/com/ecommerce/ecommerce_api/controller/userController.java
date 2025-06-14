package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.entity.User;
import com.ecommerce.ecommerce_api.service.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class userController {

    private userService userService;

    public ResponseEntity<String> login(User user){
        // log in without token first
        // did not encrypt password YET
        boolean success = userService.login(user);

        if (!success) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
        return ResponseEntity.ok("Login successful");
    }
}
