package com.fitin.shopping.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fitin.shopping.entity.Order;

public class OrderResponseDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private List<OrderItemDto> orderItems;
    private String paymentMethod;
    private String shippingAddress;
    private String orderStatus;

    // Constructors, Getters, Setters

    // 기본 생성자
    public OrderResponseDto() {
    }

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.paymentMethod = order.getPaymentMethod().toString();  // PaymentMethod enum을 String으로 변환
        this.shippingAddress = order.getShippingAddress();
        this.orderItems = order.getOrderItems().stream()
                              .map(OrderItemDto::new)
                              .collect(Collectors.toList());
    }

    // 필드 값을 직접 받는 생성자
    public OrderResponseDto(Long orderId, LocalDateTime orderDate, List<OrderItemDto> orderItems, String paymentMethod, String shippingAddress, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
        this.orderStatus = orderStatus;
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

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
