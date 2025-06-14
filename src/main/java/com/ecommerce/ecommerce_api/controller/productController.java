package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.entity.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.ecommerce.ecommerce_api.service.productService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class productController {

    // dependency

    // final means that the variable cannot be reassigned to another thing later on. Something like 'const' in js
    @Autowired
    private final productService productService;

    // create a constructor to inject productService as a dependency as product controller depends on it
    // this is to allow spring to inject the dependencies/matching bean when it sees this constructor
    public productController(productService productService) {
        this.productService = productService;
    }

    // find all the products that are inside the product repository
    @GetMapping("/allProducts")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id){
        return productService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content is not found"));
    }

    @ResponseStatus(HttpStatus.CREATED) // this should output a http status of 201 indicating successfully created
    @PostMapping("/create")
    public void create(@RequestBody Product product){
        // the @Valid syntax is to validate the input to make sure that the constraints in the Product entity is valid
        productService.save(product);
    }

    // update product
    @PutMapping("/update")
    public void update(@RequestBody Product product){

        if (!productService.existById(product.getId())){
            // if product does not exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productService.update(product, product.getId());
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id){
        if (!productService.existById(id)){
            // if product does not exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productService.delete(id);

    }

    // Search and return the list of products that is searched with the prefix.
    @GetMapping("/search")
    public List<Product> search(@RequestParam String prefix){
        return productService.search(prefix);
    }
}
