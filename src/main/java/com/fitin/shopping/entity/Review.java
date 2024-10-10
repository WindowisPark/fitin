package com.fitin.shopping.entity;

import jakarta.persistence.*;

import com.fitin.auth.entity.Member;
import com.fitin.shopping.dto.ReviewCreateDto;
import com.fitin.shopping.dto.ReviewUpdateDto;
import com.fitin.shopping.exception.ProductNotFoundException;
import com.fitin.shopping.repository.ProductRepository;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "member_id") // 외래 키 설정
    private Member member;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Review() {
        this.createdAt = LocalDateTime.now();
    }
    
    // ReviewCreateDto를 사용하는 생성자
    public Review(ReviewCreateDto reviewCreateDto) {
        this.content = reviewCreateDto.getContent();
        this.rating = reviewCreateDto.getRating();
        this.createdAt = LocalDateTime.now();
        // Product를 매핑할 때 productId로 해당 Product 엔티티를 조회해 넣어야 함
        // 이 부분은 외부에서 Product 조회 후 생성자에 전달해야 함
    }

    // 리뷰 업데이트 메서드 (ReviewUpdateDto 사용)
    public void updateReview(ReviewUpdateDto reviewUpdateDto) {
        this.content = reviewUpdateDto.getContent();
        this.rating = reviewUpdateDto.getRating();
        // updatedAt 필드를 추가해서 업데이트 시간 설정할 수도 있음
    }
    
    public Review(Product product, String content, int rating) {
        this.product = product;
        this.content = content;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
    }
    
    public Review(ReviewCreateDto reviewCreateDto, ProductRepository productRepository) {
        this.content = reviewCreateDto.getContent();
        this.createdAt = LocalDateTime.now();

        // productId로 Product 엔티티를 조회하고 할당
        this.product = productRepository.findById(reviewCreateDto.getProductId())
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    
}
