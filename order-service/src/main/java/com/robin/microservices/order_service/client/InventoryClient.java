package com.robin.microservices.order_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "Inventory", url = "http://localhost:8085")
public interface InventoryClient {

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackIsInStock")
    @Retry(name = "inventoryService")
    @RateLimiter(name = "inventoryService")
    @GetMapping("/api/inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean fallbackIsInStock(String skuCode, Integer quantity, Throwable ex) {
        System.err.println("Fallback triggered due to: " + ex.getMessage());
        return false;
    }
}



