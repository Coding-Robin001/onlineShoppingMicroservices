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
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO productDTO) {
        ProductModel product = ProductModel.builder()
                .name(productDTO.name())
                .description(productDTO.description())
                .price(productDTO.price())
                .build();
        productRepository.save(product);
        log.info("product created succesffuly");
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
                ))
                .collect(Collectors.toList());
    }

}
