package com.robin.microservices.order_service.event;

import java.math.BigDecimal;

public class OrderPlacedEvent {
    private String orderNumber;
    private BigDecimal orderPrice;

    public OrderPlacedEvent() {
    }

    public OrderPlacedEvent(String orderNumber, BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
