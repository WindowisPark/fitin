package com.fitin.profile.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartSummaryDto {
    private Long cartId;
    private List<CartItemDto> items;
    private int totalItems;
    private double totalPrice;

    @Getter
    @Setter
    public static class CartItemDto {
        private Long productId;
        private String productName;
        private int quantity;
        private double price;
    }
}