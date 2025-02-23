package com.robin.microservices.order_service.repository;


import com.robin.microservices.order_service.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderModel,Long> {
}
