package com.fitin.shopping.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDto {

    @NotBlank(message = "상품명을 입력해 주세요.")
    private String name;

    @NotBlank(message = "상품 설명을 입력해 주세요.")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be positive")
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
