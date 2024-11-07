package com.fitin.shopping.dto;

import java.math.BigDecimal;

import com.fitin.shopping.util.PaymentMethod;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentCreateDto {

    @NotNull(message = "결제 금액은 필수입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "결제 금액은 0보다 커야 합니다.")
    private BigDecimal amount;

    @NotNull(message = "결제 수단은 필수입니다.")
    private PaymentMethod paymentMethod;  // @Pattern 어노테이션 제거
    
    @NotNull(message = "주문 ID는 필수입니다.")
    private Long orderId;

    public PaymentCreateDto(BigDecimal amount, PaymentMethod paymentMethod, Long orderId) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.orderId = orderId;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public @NotNull(message = "결제 수단은 필수입니다.") PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Long getOrderId() {
        return orderId;
    }
}
