package com.ecommerce.ecommerce_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class EcommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}

}
