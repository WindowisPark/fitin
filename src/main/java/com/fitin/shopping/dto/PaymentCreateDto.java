package com.fitin.shopping.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fitin.shopping.util.PaymentMethod;

@Getter
@Setter
@NoArgsConstructor
public class PaymentCreateDto {

	@NotNull(message = "결제 금액은 필수입니다.")
	@DecimalMin(value = "0.0", inclusive = false, message = "결제 금액은 0보다 커야 합니다.")
	private BigDecimal amount;
    

    @NotNull(message = "결제 수단은 필수입니다.")
    @Pattern(regexp = "^(신용카드|계좌이체)$", message = "올바른 결제 수단을 선택해주세요.")
    private PaymentMethod paymentMethod;
    
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

    public @NotNull(message = "결제 수단은 필수입니다.") @Pattern(regexp = "^(신용카드|계좌이체)$", message = "올바른 결제 수단을 선택해주세요.") PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Long getOrderId() {
        return orderId;
    }
}
