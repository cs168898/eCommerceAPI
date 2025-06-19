package com.ecommerce.ecommerce_api.service.serviceImpl;

import com.ecommerce.ecommerce_api.dto.ApiResponse;
import com.ecommerce.ecommerce_api.dto.LoginRequest;
import com.ecommerce.ecommerce_api.dto.UserDto;
import com.ecommerce.ecommerce_api.dto.UserRegistrationRequest;
import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.Users;
import com.ecommerce.ecommerce_api.repository.userRepository;
import com.ecommerce.ecommerce_api.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
public class userServiceImpl implements userService {

    @Autowired
    private userRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse<Void> register(UserRegistrationRequest request){
        try{
            Optional<Users> existingUser = repository.findByEmail(request.getEmail());

            if(existingUser.isPresent()){
                // if the user already exists, return false
                return new ApiResponse<>(false, "Email already exists");
            }

            Users newUsers = new Users();
            newUsers.setUsername(request.getUsername());
            newUsers.setEmail(request.getEmail());

            // convert char[] to String to get the password in String format
            char[] passwordChars = request.getPasswordChars();
            String rawPassword = new String(passwordChars); // build actual password string
            String encodedPassword = passwordEncoder.encode(rawPassword);

            // Clear raw password from memory
            Arrays.fill(passwordChars, '\0');
            rawPassword = null;

            // set the newUser's encoded password
            newUsers.setPassword(encodedPassword);

            Cart cart = new Cart();
            cart.setCreatedAt(LocalDateTime.now());

            // link the cart to the user
            cart.setUser(newUsers);
            // link the user to the cart
            newUsers.setCart(cart);

            repository.save(newUsers);

            return new ApiResponse<>(true, "Account successfully created");
        } catch (Exception e) {

            return new ApiResponse<>(false, "Failed to create account -> "+ e);
        }

    }

    public ApiResponse<UserDto> login(LoginRequest request){
        try {

            Optional<Users> existingUser = repository.findByEmail(request.getEmail());
            if(existingUser.isEmpty()){
                // if the user does not exist, Return false.
                return new ApiResponse<>(false, "User does not exist");
            }
            String existingUserPasswordString = existingUser.get().getPassword();

            // use this as we will accept the password as a list of array later on
            String raw = new String(request.getPassword());
            boolean match = passwordEncoder.matches(raw, existingUserPasswordString);

            Arrays.fill(request.getPassword(), '\0'); // clear char[] memory
            if(match){
                // if successful login, get the user info

                return new ApiResponse<>(true, "Log in Successful", UserDto.toUserDto(existingUser.get()));
            } else{
                return new ApiResponse<>(true, "Invalid email or password");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
