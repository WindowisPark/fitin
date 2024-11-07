package com.fitin.shopping.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.auth.entity.Member;
import com.fitin.shopping.dto.CartDetailDto;
import com.fitin.shopping.dto.CartDetailDto.CartItemInfo;
import com.fitin.shopping.dto.CartItemDto;
import com.fitin.shopping.entity.Cart;
import com.fitin.shopping.entity.CartItem;
import com.fitin.shopping.entity.Product;
import com.fitin.shopping.repository.CartItemRepository;
import com.fitin.shopping.repository.CartRepository;
import com.fitin.shopping.repository.ProductRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }
    
    
    // 장바구니에 상품 추가 (Member 매개변수 추가)
    @Transactional
    public void addItemToCart(Member member, CartItemDto cartItemDto) {
        // 멤버의 장바구니 찾기 또는 생성
        Cart cart = cartRepository.findByMember(member)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setMember(member);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        CartItem cartItem = new CartItem(cart, product, cartItemDto.getQuantity());
        cartItemRepository.save(cartItem);
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
    
 // 장바구니 상품 목록 조회 (Member 매개변수 추가)
    @Transactional(readOnly = true)
    public List<CartItemInfo> getCartItems(Member member) {
        Cart cart = cartRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));
        return cartItemRepository.findCartItemInfoList(cart.getId());
    }
    
    @Transactional(readOnly = true)
    public int getCartItemCount(Member member) {
        Cart cart = cartRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("사용자의 장바구니를 찾을 수 없습니다."));
        return cartItemRepository.countByCartId(cart.getId());
    }
    
    @Transactional(readOnly = true)
    public CartDetailDto getCartDetails(Long cartId) {
        List<CartDetailDto.CartItemInfo> items = cartItemRepository.findCartItemInfoList(cartId);
        BigDecimal totalAmount = cartItemRepository.calculateCartTotalAmount(cartId);
        
        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        return new CartDetailDto(cartId, items, totalAmount);
    }
}
