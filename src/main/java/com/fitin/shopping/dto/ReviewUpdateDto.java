package com.fitin.shopping.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewUpdateDto {

    @NotNull(message = "리뷰 ID는 필수입니다.")
    private Long reviewId;

    @NotBlank(message = "리뷰 내용은 필수입니다.")
    private String content;

    @NotNull(message = "평점은 필수입니다.")
    @Min(value = 1, message = "평점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "평점은 최대 5점이어야 합니다.")
    private Integer rating;

    // 새로운 이미지 업로드
    private MultipartFile newImage;

    // 이미지 삭제 여부
    private boolean deleteImage;

    // Getters, Setters, Constructors

    public ReviewUpdateDto() {}

    public ReviewUpdateDto(Long reviewId, String content, Integer rating, MultipartFile newImage, boolean deleteImage) {
        this.reviewId = reviewId;
        this.content = content;
        this.rating = rating;
        this.newImage = newImage;
        this.deleteImage = deleteImage;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
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

    public MultipartFile getNewImage() {
        return newImage;
    }

    public void setNewImage(MultipartFile newImage) {
        this.newImage = newImage;
    }

    public boolean isDeleteImage() {
        return deleteImage;
    }

    public void setDeleteImage(boolean deleteImage) {
        this.deleteImage = deleteImage;
    }
}
