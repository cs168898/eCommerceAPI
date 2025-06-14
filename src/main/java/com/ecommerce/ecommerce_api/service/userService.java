package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.User;
import com.ecommerce.ecommerce_api.repository.userRepository;

public interface userService {

    public boolean login(User user);

}
