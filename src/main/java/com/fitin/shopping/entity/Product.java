package com.fitin.shopping.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fitin.shopping.dto.ProductCreateDto;
import com.fitin.shopping.dto.ProductUpdateDto;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    @JoinTable(
       name = "product_category",
       joinColumns = @JoinColumn(name = "product_id"),
       inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonManagedReference
    private List<Category> categories = new ArrayList<>();
    
    private String name;
    private String description;
    private BigDecimal price;  // price 필드를 BigDecimal로 변경
    private int stockQuantity;
    private String imageUrl;  // 이미지 URL 필드 추가
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    
    public Product(String name, String description, BigDecimal price, Integer stockQuantity, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProduct(ProductUpdateDto productUpdateDto) {
        this.name = productUpdateDto.getName();
        this.description = productUpdateDto.getDescription();
        this.price = productUpdateDto.getPrice();   // BigDecimal 타입
        this.stockQuantity = productUpdateDto.getStockQuantity();
        this.imageUrl = productUpdateDto.getImageUrl(); // imageUrl 업데이트 추가
    }

    public Product(ProductCreateDto productCreateDto) {
        this.name = productCreateDto.getName();
        this.description = productCreateDto.getDescription();
        this.price = productCreateDto.getPrice();
        this.stockQuantity = productCreateDto.getStockQuantity();
        this.imageUrl = (String) productCreateDto.getImageUrl();
    }

}
