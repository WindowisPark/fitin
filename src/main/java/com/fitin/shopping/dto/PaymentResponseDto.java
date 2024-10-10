package com.fitin.shopping.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponseDto {

    private Long paymentId;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private Long orderId;

    public PaymentResponseDto(Long paymentId, BigDecimal amount, String paymentMethod, LocalDateTime paymentDate, Long orderId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.orderId = orderId;
    }

    // Getters
    public Long getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public Long getOrderId() {
        return orderId;
    }
}
