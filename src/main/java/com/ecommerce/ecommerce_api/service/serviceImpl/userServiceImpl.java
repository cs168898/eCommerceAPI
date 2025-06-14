package com.ecommerce.ecommerce_api.service.serviceImpl;

import com.ecommerce.ecommerce_api.entity.User;
import com.ecommerce.ecommerce_api.repository.userRepository;

import java.util.Optional;

public class userServiceImpl {
    private userRepository userRepository;

    public boolean login(User currentUser){

        char[] currentUserPassword = currentUser.getPassword();
        boolean response = false;

        Optional<User> existingUser = userRepository.findByEmail(currentUser.getEmail());
        if (existingUser.isPresent()){
            char[] existingUserPassword = existingUser.get().getPassword();

            for(int i = 0; i < existingUserPassword.length; i++){

                // if the characters for both passwords do not match
                if(existingUserPassword[i] != currentUserPassword[i]){
                    return response;
                }
            }
        }

        return response;
    }
}
