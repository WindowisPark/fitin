package com.fitin.shopping.dto;

import com.fitin.shopping.util.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class OrderCreateDto {

    private Long memberId;

    @NotNull(message = "주문할 상품 목록은 필수입니다.")
    private List<OrderItemDto> orderItems;

    @NotNull(message = "결제 수단은 필수입니다.")
    private PaymentMethod paymentMethod;

    @NotBlank(message = "배송 주소는 필수입니다.")
    private String shippingAddress;

    // Constructors
    public OrderCreateDto() {}

    public OrderCreateDto(List<OrderItemDto> orderItems, PaymentMethod paymentMethod, String shippingAddress) {
        this.orderItems = orderItems;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
    }

    // Getters and Setters
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    // String으로 입력된 결제 수단을 PaymentMethod Enum으로 변환하여 할당
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = PaymentMethod.fromString(paymentMethod); 
    }

    public String getPaymentMethodDisplayName() {
        return paymentMethod != null ? paymentMethod.getDisplayName() : null;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
