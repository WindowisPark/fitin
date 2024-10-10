package com.fitin.shopping.dto;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public class ProductUpdateDto {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long id;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    private String description;

    @NotNull(message = "가격은 필수입니다.")
    private BigDecimal price;

    private Integer stockQuantity;
    
    private String imageUrl;

    // Constructors, Getters, Setters

    public ProductUpdateDto() {}

    public ProductUpdateDto(Long id, String name, String description, @NotNull(message = "가격은 필수입니다.") BigDecimal price, Integer stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "가격은 필수입니다.") BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
