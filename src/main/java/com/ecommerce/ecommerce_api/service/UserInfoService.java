package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.Users;
import com.ecommerce.ecommerce_api.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    // this is a service that queries your DB then converts into the userInfoDetails class to send into
    // spring security to authenticate

    @Autowired
    private final userRepository repository;


    @Autowired
    public UserInfoService(userRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userDetail = repository.findByEmail(username);

        // converting User class to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

}
