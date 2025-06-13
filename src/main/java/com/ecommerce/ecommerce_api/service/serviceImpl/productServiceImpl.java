package com.ecommerce.ecommerce_api.service.serviceImpl;

import com.ecommerce.ecommerce_api.entity.Product;
import com.ecommerce.ecommerce_api.repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommerce_api.service.productService;

import java.util.List;
import java.util.Optional;

@Service
public class productServiceImpl implements productService {

    @Autowired
    private productRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
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
}
