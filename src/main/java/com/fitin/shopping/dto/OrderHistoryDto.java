package com.fitin.shopping.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fitin.shopping.entity.Order;
import com.fitin.shopping.entity.OrderItem;

public class OrderHistoryDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String orderStatus;

    // Constructors, Getters, Setters

    public OrderHistoryDto() {}

    public OrderHistoryDto(Long orderId, LocalDateTime orderDate, BigDecimal totalAmount, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }

    public OrderHistoryDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate();
        this.totalAmount = calculateTotalAmount(order);
        this.orderStatus = order.getStatus();
    }
    
    private BigDecimal calculateTotalAmount(Order order) {
        return order.getOrderItems().stream()
            .map(this::calculateItemTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateItemTotal(OrderItem item) {
        return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
