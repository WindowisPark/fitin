package com.fitin.shopping.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainProductDto {

    private Long id;               // 상품 ID
    private String name;           // 상품명
    private String description;    // 상품 간략 설명
    private String imgUrl;         // 상품 대표 이미지 URL
    private BigDecimal price;             // 상품 가격

    public MainProductDto(Long id, String name, String description, String imgUrl, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
