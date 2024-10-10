package com.fitin.shopping.controller;

import com.fitin.auth.entity.Member;
import com.fitin.shopping.dto.MainPageDto;
import com.fitin.shopping.service.CartService;
import com.fitin.shopping.service.CategoryService;
import com.fitin.shopping.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
public class MainController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;


    public MainController(ProductService productService, CategoryService categoryService, CartService cartService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cartService = cartService;
    }

    @GetMapping
    // 1. 인증된 사용자의 경우 장바구니 아이템 개수를 정확히 가져올 수 있음.
    // 2. 인증되지 않은 사용자의 경우 장바구니 아이템 개수를 0으로 설정.
    
    public ResponseEntity<MainPageDto> getMainPageData(@AuthenticationPrincipal Member member) {
        int cartItemCount = 0;
        if (member != null) {
            cartItemCount = cartService.getCartItemCount(member);
        }

        MainPageDto mainPageDto = MainPageDto.builder()
            .bestsellers(productService.getBestsellers())
            .newArrivals(productService.getNewArrivals())
            .recommendedProducts(productService.getRecommendedProducts())
            .categories(categoryService.getAllCategories())
            .recentlyViewedProducts(productService.getRecentlyViewedProducts())
            .cartItemCount(cartItemCount)
            .build();

        return ResponseEntity.ok(mainPageDto);
    }
}