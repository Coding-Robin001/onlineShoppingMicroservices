package com.robin.microservices.product_service.service;

import com.robin.microservices.product_service.dto.ProductRequestDTO;
import com.robin.microservices.product_service.dto.ProductResponseDTO;
import com.robin.microservices.product_service.model.ProductModel;
import com.robin.microservices.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productDTO) {
        ProductModel product = new ProductModel();
        product.setName(productDTO.name());
        product.setDescription(productDTO.description());
        product.setPrice(productDTO.price());

        productRepository.save(product);
//        log.info("product created successfully");
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<ProductModel> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                )).collect(Collectors.toList());
    }
}
