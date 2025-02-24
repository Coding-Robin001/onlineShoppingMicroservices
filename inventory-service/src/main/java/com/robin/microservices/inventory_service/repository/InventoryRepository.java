package com.robin.microservices.inventory_service.repository;

import com.robin.microservices.inventory_service.model.InventoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryModel, Long> {
    boolean existsBySkucodeAndQuantityIsGreaterThanEquals(String skuCode, Integer quantity);
}
