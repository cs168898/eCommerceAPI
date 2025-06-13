package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Product;
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
public interface productRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT * " +
            "FROM Product" +
            "WHERE Product.name LIKE s%")
    List<Product> findByNameStartingWith(@Param("prefix") String prefix);

}
