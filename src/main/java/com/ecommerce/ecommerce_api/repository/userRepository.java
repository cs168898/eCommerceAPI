package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Product;
import com.ecommerce.ecommerce_api.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User, Integer> {
    // For login by email
    Optional<User> findByEmail(String email);

    // Or, if you're using username instead
    Optional<User> findByUsername(String username);
}
