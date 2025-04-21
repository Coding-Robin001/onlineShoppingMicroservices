package com.robin.microservices.order_service.service;

import com.robin.microservices.order_service.client.InventoryClient;
import com.robin.microservices.order_service.dto.OrderRequestDTO;
import com.robin.microservices.order_service.dto.OrderResponseDTO;
import com.robin.microservices.order_service.event.OrderPlacedEvent;
import com.robin.microservices.order_service.model.OrderModel;
import com.robin.microservices.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient, KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO){

        var isProductInStock = inventoryClient.isInStock(orderRequestDTO.skuCode(), orderRequestDTO.quantity());

        if (isProductInStock){
            OrderModel order = new OrderModel();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequestDTO.price());
            order.setSkuCode(orderRequestDTO.skuCode());
            order.setQuantity(orderRequestDTO.quantity());

            orderRepository.save(order);
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(orderRequestDTO.userDetails().email());
            orderPlacedEvent.setFirstName(orderRequestDTO.userDetails().firstName());
            orderPlacedEvent.setLastName((orderRequestDTO.userDetails().lastName()));
            log.info("start - sending orderPlacedEvent to kafka topic order-placed ",
                    orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("end - sending orderPlacedEvent to kafka topic order-placed ",
                    orderPlacedEvent);
            return new OrderResponseDTO(
                    order.getId(),
                    order.getOrderNumber(),
                    order.getSkuCode(),
                    order.getPrice(),
                    order.getQuantity()
            );
        } else {
            throw new RuntimeException("product with skuCode " + orderRequestDTO.skuCode() + " is not in stock");
        }
    }

        public List<OrderResponseDTO> getAllOrders() {
            List<OrderModel> orders = orderRepository.findAll();

            return orders.stream()
                    .map(order -> new OrderResponseDTO(
                            order.getId(),
                            order.getOrderNumber(),
                            order.getSkuCode(),
                            order.getPrice(),
                            order.getQuantity()
                    ))
                    .collect(Collectors.toList());
        }
}
