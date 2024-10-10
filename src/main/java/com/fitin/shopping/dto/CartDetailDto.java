package com.fitin.shopping.dto;

import java.math.BigDecimal;

public class CartDetailDto {

    private Long id;           // CartItem의 ID
    private String productName; // Product 이름
    private Integer quantity;  // CartItem의 수량
    private BigDecimal price;      // Product 가격

    // 생성자
    public CartDetailDto(Long id, String productName, Integer quantity, BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
