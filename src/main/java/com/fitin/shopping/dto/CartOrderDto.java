package com.fitin.shopping.dto;

import java.util.List;

public class CartOrderDto {

    private List<Long> cartItemIds;

    // Constructors, Getters, Setters

    public CartOrderDto() {}

    public CartOrderDto(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }

    public void setCartItemIds(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }
}
