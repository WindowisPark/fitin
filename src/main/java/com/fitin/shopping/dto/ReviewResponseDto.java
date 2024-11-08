package com.fitin.shopping.dto;

import com.fitin.shopping.entity.Review;

public class ReviewResponseDto {

    private Long reviewId;
	private Long productId;
    private String content;
    private Integer rating;
    // 이미지 URL 필드 추가
    private String imageUrl;

    // Getters, Setters, Constructors

    public ReviewResponseDto() {}

    public ReviewResponseDto(Review review) {
        review.getId();
        this.productId = review.getProduct().getId();  // Review 엔티티의 Product 필드를 참조하여 초기화
        this.content = review.getContent();
        this.rating = review.getRating();
        review.getCreatedAt();
    }
    
    public ReviewResponseDto(Long reviewId, Long productId, String content, Integer rating, String imageUrl) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.content = content;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
