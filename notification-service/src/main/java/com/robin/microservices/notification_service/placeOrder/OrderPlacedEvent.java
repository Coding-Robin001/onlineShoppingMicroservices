package com.robin.microservices.notification_service.placeOrder;

public class OrderPlacedEvent {
    private String orderNumber;
    private String email;

    public OrderPlacedEvent() {}

    public OrderPlacedEvent(String email, String orderNumber) {
        this.email = email;
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
