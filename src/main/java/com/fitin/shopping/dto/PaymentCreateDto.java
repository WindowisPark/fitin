package com.fitin.shopping.dto;

import java.math.BigDecimal;

public class PaymentCreateDto {

    private BigDecimal amount;
    private String paymentMethod;
    private Long orderId;

    public PaymentCreateDto() {}

    public PaymentCreateDto(BigDecimal amount, String paymentMethod, Long orderId) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Long getOrderId() {
        return orderId;
    }
}
