package com.fitin.shopping.controller;

import com.fitin.shopping.dto.CartItemDto;
import com.fitin.shopping.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니에 상품 추가
    @PostMapping("/{cartId}/items")
    public ResponseEntity<Void> addItemToCart(@PathVariable Long cartId, @RequestBody CartItemDto cartItemDto) {
        cartService.addItemToCart(cartId, cartItemDto);
        return ResponseEntity.ok().build();
    }

    // 장바구니 상품 목록 조회
    @GetMapping("/{cartId}/items")
    public ResponseEntity<?> getCartItems(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCartItems(cartId));
    }

    // 장바구니 아이템 삭제
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    // 장바구니 아이템 업데이트
    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(@PathVariable Long cartItemId, @RequestBody CartItemDto cartItemDto) {
        cartService.updateCartItem(cartItemId, cartItemDto);
        return ResponseEntity.ok().build();
    }
}
