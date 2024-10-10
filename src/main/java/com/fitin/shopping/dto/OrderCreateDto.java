package com.fitin.shopping.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderCreateDto {

    @NotNull(message = "주문할 상품 목록은 필수입니다.")
    private List<OrderItemDto> orderItems;

    @NotNull(message = "결제 수단은 필수입니다.")
    private String paymentMethod;

    private String shippingAddress;

    // Constructors, Getters, Setters

    public OrderCreateDto() {}

    public OrderCreateDto(List<OrderItemDto> orderItems, String paymentMethod, String shippingAddress) {
        this.orderItems = orderItems;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
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
}
