package com.robin.microservices.order_service.dto;

import java.math.BigDecimal;

public record OrderRequestDTO(Long id,String OrderNumber, String skuCode, BigDecimal price,Integer quantity) {
}
