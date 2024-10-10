package com.fitin.shopping.repository;

import com.fitin.shopping.entity.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {
    
    // 특정 리뷰에 대한 이미지 목록 조회
    List<ReviewImg> findByReviewId(Long reviewId);
}
