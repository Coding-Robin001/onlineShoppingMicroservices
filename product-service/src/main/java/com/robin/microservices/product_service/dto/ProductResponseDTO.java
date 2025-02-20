package com.robin.microservices.product_service.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String name, String description, BigDecimal price) {
}
