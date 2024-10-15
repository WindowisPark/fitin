package com.fitin.shopping.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fitin.shopping.util.PaymentMethod;

public class PaymentResponseDto {

	
	
    private Long paymentId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private Long orderId;

    public PaymentResponseDto(Long paymentId, BigDecimal amount, PaymentMethod paymentMethod, LocalDateTime paymentDate, Long orderId) {
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentMethodDisplayName() {
        return paymentMethod.getDisplayName();
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public Long getOrderId() {
        return orderId;
    }
}
