package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.associativeEntity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cartItemRepository extends JpaRepository<CartItem, Integer> {

}
