package com.robin.microservices.product_service;

import com.robin.microservices.product_service.model.ProductModel;
import com.robin.microservices.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ProductServiceApplication {

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData() {
		return args -> {
			// Check if data exists to prevent duplicate inserts
			if (productRepository.count() == 0) {
                productRepository.save(new ProductModel(null, "iphone_12", "latestIphone", new BigDecimal("750.00")));
                productRepository.save(new ProductModel(null, "galaxy_5", "latestGalaxy", new BigDecimal("550.00")));
                productRepository.save(new ProductModel(null, "pixel_8", "latestPixel", new BigDecimal("600.00")));
                productRepository.save(new ProductModel(null, "onePlus_11", "latest-OnePlus", new BigDecimal("990.00")));
				System.out.println("product data inserted successfully!");
			} else {
				System.out.println("Product data already exists. Skipping insertion.");
			}
		};
	}

}
