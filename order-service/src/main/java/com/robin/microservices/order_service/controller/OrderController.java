package com.robin.microservices.order_service.controller;

import com.robin.microservices.order_service.dto.OrderRequestDTO;
import com.robin.microservices.order_service.dto.OrderResponseDTO;
import com.robin.microservices.order_service.dto.OrderResponseWrapper;
import com.robin.microservices.order_service.repository.OrderRepository;
import com.robin.microservices.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseWrapper placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO orderResponse = orderService.placeOrder(orderRequestDTO);
        return new OrderResponseWrapper("Order placed successfully", orderResponse);
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

}
