package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.ApiResponse;
import com.ecommerce.ecommerce_api.dto.LoginRequest;
import com.ecommerce.ecommerce_api.dto.UserRegistrationRequest;
import com.ecommerce.ecommerce_api.entity.Users;
import com.ecommerce.ecommerce_api.service.userService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class userController {

    @Autowired
    private userService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest user){
        ApiResponse<Void> response = userService.register(user);
        if (!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response.getMessage());
        }
        return ResponseEntity.ok(response.getMessage());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Users>> login(@RequestBody LoginRequest request){
        // log in without token first
        // did not encrypt password YET
        ApiResponse<Users> response = userService.login(request);

        if (!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        return ResponseEntity.ok(response);
    }
}
