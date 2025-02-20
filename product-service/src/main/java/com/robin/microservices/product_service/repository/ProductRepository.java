package com.robin.microservices.product_service.repository;

import com.robin.microservices.product_service.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
