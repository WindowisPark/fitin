package com.fitin.shopping.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateDto {

    @NotBlank(message = "상품명을 입력해 주세요.")
    private String name;

    @NotBlank(message = "상품 설명을 입력해 주세요.")
    private String description;

    @NotNull(message = "가격을 입력해 주세요.")
    private BigDecimal price;

    @NotNull(message = "재고 수량을 입력해 주세요.")
    private Integer stockQuantity;

    private String category;
    
    private String imageUrl;

    // 생성자, getter, setter
    public ProductCreateDto() {
    }

    public ProductCreateDto(String name, String description, BigDecimal price, Integer stockQuantity, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    
}