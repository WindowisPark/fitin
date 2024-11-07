package com.fitin.shopping.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDetailDto {
    private Long cartId;
    private List<CartItemInfo> items;
    private BigDecimal totalAmount;

    public CartDetailDto(Long cartId, List<CartItemInfo> items, BigDecimal totalAmount) {
        this.cartId = cartId;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    // Getters
    public Long getCartId() { return cartId; }
    public List<CartItemInfo> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }

    public static class CartItemInfo {
        private Long id;
        private String productName;
        private Integer quantity;
        private BigDecimal price;

        public CartItemInfo(Long id, String productName, Integer quantity, BigDecimal price) {
            this.id = id;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }
        // Getters
        public Long getId() { return id; }
        public String getProductName() { return productName; }
        public Integer getQuantity() { return quantity; }
        public BigDecimal getPrice() { return price; }

    }

}