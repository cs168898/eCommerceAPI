package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class cartContentRepository extends JpaRepository<Cart, Integer> {

    // the cart should contain a list of different products
    private final List<Product> cartContent = new ArrayList<>();

    public cartContentRepository() {
    }

    public List<Product> findAll(){
        return cartContent;
    }

    public Optional<Product> findById(Integer id){
        return cartContent.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
