package com.fitin.shopping.repository;

import com.fitin.shopping.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 상품에 대한 리뷰 목록 조회 (최신 순 정렬)
    @Query("select r from Review r where r.product.id = :productId order by r.createdAt desc")
    List<Review> findReviewsByProductId(@Param("productId") Long productId);

    // 특정 사용자가 작성한 리뷰 목록 조회 (최신 순 정렬)
    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId ORDER BY r.createdAt DESC")
    List<Review> findReviewsByMemberId(@Param("memberId") Long memberId);
    
}
