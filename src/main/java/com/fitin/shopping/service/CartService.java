package com.fitin.shopping.service;

import com.fitin.auth.entity.Member;
import com.fitin.shopping.dto.CartDetailDto;
import com.fitin.shopping.dto.CartItemDto;
import com.fitin.shopping.entity.Cart;
import com.fitin.shopping.entity.CartItem;
import com.fitin.shopping.entity.Product;
import com.fitin.shopping.repository.CartItemRepository;
import com.fitin.shopping.repository.CartRepository;
import com.fitin.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    // 장바구니에 상품 추가
    @Transactional
    public void addItemToCart(Long cartId, CartItemDto cartItemDto) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));
        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        
        CartItem cartItem = new CartItem(cart, product, cartItemDto.getQuantity());
        cartItemRepository.save(cartItem);
    }

    // 장바구니 상품 목록 조회
    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartItems(Long cartId) {
        return cartItemRepository.findCartDetailDtoList(cartId);
    }

    // 장바구니 아이템 삭제
    @Transactional
    public void removeItemFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // 장바구니 아이템 업데이트
    @Transactional
    public void updateCartItem(Long cartItemId, CartItemDto cartItemDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItemRepository.save(cartItem);
    }
    
    @Transactional(readOnly = true)
    public int getCartItemCount(Member member) {
        Cart cart = cartRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("사용자의 장바구니를 찾을 수 없습니다."));
        return cartItemRepository.countByCartId(cart.getId());
    }
    
}
