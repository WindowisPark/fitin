package com.fitin.shopping.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fitin.shopping.entity.Product;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

	 	private Long id;
	   	private String name;
	    private String description;
	    private BigDecimal price;
	    private int stockQuantity;
	    private String imageUrl;  // imgList 대신 imageUrl 사용
	    
    
	// 기본 생성자
	public ProductDto() {} 
    
	 // Product 엔티티를 받는 생성자
    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.imageUrl = product.getImageUrl();
    }

	public ProductDto(Long id, String name, String description, BigDecimal price, int stockQuantity, String imageUrl) {
	    this.id = id;
	    this.name = name;
	    this.description = description;
	    this.price = price;
	    this.stockQuantity = stockQuantity;
	    this.imageUrl = imageUrl;
	}
	
    public ProductDto(Product product, List<ProductImgDto> imgList) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.imageUrl = product.getImageUrl();
    }


}
