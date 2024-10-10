package com.fitin.shopping.dto;

import com.fitin.shopping.entity.ProductImg;

//상품 이미지 정보를 담는 DTO 클래스
public class ProductImgDto {

 private Long id;               // 이미지 ID
 private Long productId;        // 해당 상품 ID
 private String imgUrl;         // 이미지 URL
 private boolean isRepresentative;  // 대표 이미지 여부

 // 기본 생성자
 public ProductImgDto() {
 }

 // 생성자
 public ProductImgDto(Long id, Long productId, String imgUrl, boolean isRepresentative) {
     this.id = id;
     this.productId = productId;
     this.imgUrl = imgUrl;
     this.isRepresentative = isRepresentative;
 }
 public ProductImgDto(ProductImg productImg) {
     this.id = productImg.getId();
     this.imgUrl = productImg.getImgUrl();
 }

 // Getter 및 Setter
 public Long getId() {
     return id;
 }

 public void setId(Long id) {
     this.id = id;
 }

 public Long getProductId() {
     return productId;
 }

 public void setProductId(Long productId) {
     this.productId = productId;
 }

 public String getImgUrl() {
     return imgUrl;
 }

 public void setImgUrl(String imgUrl) {
     this.imgUrl = imgUrl;
 }

 public boolean isRepresentative() {
     return isRepresentative;
 }

 public void setRepresentative(boolean representative) {
     isRepresentative = representative;
 }
}
