package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.dto.ProductDto;
import com.ecommerce.ecommerce_api.entity.Product;

import java.util.List;
import java.util.Optional;

public interface productService {
    List<ProductDto> findAll();

    Optional<Product> findById(Integer id);

    void save(Product product);

    boolean existById(Integer id);

    void update(Product product , Integer id);

    void delete(Integer id);

    List<Product> search(String prefix);
}
