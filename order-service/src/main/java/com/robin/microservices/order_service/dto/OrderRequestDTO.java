package com.robin.microservices.order_service.dto;

import java.math.BigDecimal;

public record OrderRequestDTO(Long id,String OrderNumber, String skuCode, BigDecimal price,Integer quantity, UserDetails userDetails) {

    public record UserDetails(String email, String firstName, String lastName){

    }
}
