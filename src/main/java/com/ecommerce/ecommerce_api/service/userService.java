package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.dto.ApiResponse;
import com.ecommerce.ecommerce_api.dto.LoginRequest;
import com.ecommerce.ecommerce_api.dto.UserRegistrationRequest;
import com.ecommerce.ecommerce_api.entity.Users;

public interface userService {

    public ApiResponse<Users> login(LoginRequest user);

    public ApiResponse<Void> register(UserRegistrationRequest request);
}
