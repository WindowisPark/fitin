package com.fitin.shopping.service;

import com.fitin.shopping.dto.ReviewCreateDto;
import com.fitin.shopping.dto.ReviewResponseDto;
import com.fitin.shopping.dto.ReviewUpdateDto;
import com.fitin.shopping.entity.Review;
import com.fitin.shopping.exception.ReviewNotFoundException;
import com.fitin.shopping.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 생성
    @Transactional
    public ReviewResponseDto createReview(ReviewCreateDto reviewCreateDto) {
        Review review = new Review(reviewCreateDto);
        reviewRepository.save(review);
        return new ReviewResponseDto(review);
    }

    // 리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewUpdateDto reviewUpdateDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));
        review.updateReview(reviewUpdateDto);
        reviewRepository.save(review);
        return new ReviewResponseDto(review);
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException("리뷰를 찾을 수 없습니다.");
        }
        reviewRepository.deleteById(reviewId);
    }

    // 특정 상품에 대한 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsByProduct(Long productId) {
        return reviewRepository.findReviewsByProductId(productId)
                .stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 사용자에 대한 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsByUser(Long memberId) {
        return reviewRepository.findReviewsByMemberId(memberId)
                .stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    // 전체 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }
}
