package com.fitin.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitin.auth.entity.Member;
import com.fitin.shopping.dto.CartItemDto;
import com.fitin.shopping.entity.Cart;
import com.fitin.shopping.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<Void> addItemToCart(
            @AuthenticationPrincipal Member member,
            @RequestBody CartItemDto cartItemDto) {
        cartService.addItemToCart(member, cartItemDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/items")
    public ResponseEntity<?> getCartItems(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(cartService.getCartItems(member));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody CartItemDto cartItemDto) {
        cartService.updateCartItem(cartItemId, cartItemDto);
        return ResponseEntity.ok().build();
    }
}
