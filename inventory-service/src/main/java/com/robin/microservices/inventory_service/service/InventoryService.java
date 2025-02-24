package com.robin.microservices.inventory_service.service;

import com.robin.microservices.inventory_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Boolean isInStock(String skuCode, Integer quantity){
        return inventoryRepository.existsBySkucodeAndQuantityIsGreaterThanEquals(skuCode, quantity);
    }

}
