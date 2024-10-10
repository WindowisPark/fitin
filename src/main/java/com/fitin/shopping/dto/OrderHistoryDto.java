package com.fitin.shopping.dto;

import java.time.LocalDateTime;

import com.fitin.shopping.entity.Order;

public class OrderHistoryDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String orderStatus;

    // Constructors, Getters, Setters

    public OrderHistoryDto() {}

    public OrderHistoryDto(Long orderId, LocalDateTime orderDate, Double totalAmount, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }

    public OrderHistoryDto(Order order) {
        // Order 정보를 바탕으로 필드 값 초기화
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
