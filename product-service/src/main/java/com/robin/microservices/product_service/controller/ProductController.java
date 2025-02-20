package com.robin.microservices.product_service.controller;

import com.robin.microservices.product_service.dto.ProductRequestDTO;
import com.robin.microservices.product_service.dto.ProductResponseDTO;
import com.robin.microservices.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    ProductService productService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> getAllProducts(){
        return productService.getAllProducts();
    }
}
