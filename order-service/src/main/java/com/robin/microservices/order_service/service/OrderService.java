package com.robin.microservices.order_service.service;

import com.robin.microservices.order_service.dto.OrderRequestDTO;
import com.robin.microservices.order_service.dto.OrderResponseDTO;
import com.robin.microservices.order_service.model.OrderModel;
import com.robin.microservices.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO){
        OrderModel order = new OrderModel();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequestDTO.price());
        order.setSkuCode(orderRequestDTO.skuCode());
        order.setQuantity(orderRequestDTO.quantity());

        orderRepository.save(order);

        return new OrderResponseDTO(
                order.getId(),
                order.getOrderNumber(),
                order.getSkuCode(),
                order.getPrice(),
                order.getQuantity()
        );
    }


        public List<OrderResponseDTO> getAllOrders() {
            List<OrderModel> orders = orderRepository.findAll();

            return orders.stream()
                    .map(order -> new OrderResponseDTO(
                            order.getId(),  // Convert Long to String
                            order.getOrderNumber(),
                            order.getSkuCode(),
                            order.getPrice(),
                            order.getQuantity()
                    ))
                    .collect(Collectors.toList());
        }

}
