package com.fitin.shopping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MainPageDto {
	
    private List<ProductDto> bestsellers;
    private List<ProductDto> newArrivals;
    private List<ProductDto> recommendedProducts;
    private List<CategoryDto> categories;
    private List<ProductDto> recentlyViewedProducts;
    private int cartItemCount;

    // 생성자, getter, setter 등은 lombok이 자동으로 생성합니다.
}