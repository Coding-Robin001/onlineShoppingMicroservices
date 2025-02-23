package com.robin.microservices.order_service.dto;

public record OrderResponseWrapper(String message, OrderResponseDTO order) {
}
