package com.fitin.shopping.dto;

public class ReviewDto {
    
    private Long reviewId;
    private Long productId;
    private Long userId;
    private String content;
    private int rating;
    private String createdAt;

    // 기본 생성자
    public ReviewDto() {}

    // 생성자
    public ReviewDto(Long reviewId, Long productId, Long userId, String content, int rating, String createdAt) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    // Getter와 Setter
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
