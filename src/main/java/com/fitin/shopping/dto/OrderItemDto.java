package com.fitin.shopping.dto;

import java.math.BigDecimal;

import com.fitin.shopping.entity.OrderItem;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemDto {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @NotNull(message = "주문 수량은 필수입니다.")
    @Min(value = 1, message = "최소 1개의 상품을 주문해야 합니다.")
    @Max(value = 100, message = "최대 100개의 상품만 주문할 수 있습니다.")
    private Integer quantity;

	@SuppressWarnings("unused")
	private BigDecimal price;



    // Constructors, Getters, Setters

    public OrderItemDto() {}

    public OrderItemDto(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    
    public OrderItemDto(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();  // 혹은 Product 엔티티에서 id를 가져옴
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
