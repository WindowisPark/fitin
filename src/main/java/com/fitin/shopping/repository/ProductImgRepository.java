package com.fitin.shopping.repository;

import com.fitin.shopping.entity.Product;
import com.fitin.shopping.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    
    // 특정 상품의 이미지 목록을 조회하는 메서드
    List<ProductImg> findByProductId(Long productId);
    
    // 대표 이미지를 찾는 메서드 (상품의 대표 이미지를 가져올 때)
    ProductImg findByProductIdAndIsMainImage(Long productId, boolean isMainImage);
    
    // 특정 Product에 속하는 모든 이미지 조회
    List<ProductImg> findByProduct(Product product);
}
