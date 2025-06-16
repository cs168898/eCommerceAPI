package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<Users, Integer> {
    // For login by email
    Optional<Users> findByEmail(String email);

    // Or, if you're using username instead
    Optional<Users> findByUsername(String username);
}
