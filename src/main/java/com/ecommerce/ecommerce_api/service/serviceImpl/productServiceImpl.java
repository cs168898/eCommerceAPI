package com.ecommerce.ecommerce_api.service.serviceImpl;

import com.ecommerce.ecommerce_api.dto.ProductDto;
import com.ecommerce.ecommerce_api.entity.Product;
import com.ecommerce.ecommerce_api.repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommerce_api.service.productService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class productServiceImpl implements productService {

    @Autowired
    private productRepository productRepository;

    public List<ProductDto> findAll(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // helper method to perform the conversion
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setUrl(product.getUrl());
        return dto;
    }

    // finds and return the product
    public Optional<Product> findById(Integer id){
        return productRepository.findById(id);
    }

    //method to create and save a product
    public void save(Product product){
        productRepository.save(product);
    }

    // method to check if the product exist by id
    public boolean existById(Integer id){
        return productRepository.existsById(id);
    }

    //update by deleting previous product of the same id and re-adding it.
    public void update(Product product , Integer id){

        Product existing = productRepository.findById(id).orElseThrow();

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        existing.setCreatedAt(product.getCreatedAt());
        existing.setUpdatedAt(product.getUpdatedAt());

        productRepository.save(existing);
    }

    public void delete(Integer id){
        productRepository.deleteById(id);
    }

    // Search for the products that are starting with the prefix
    public List<Product> search(String prefix){
        return productRepository.findByNameStartingWithIgnoreCase(prefix);
    }
}
