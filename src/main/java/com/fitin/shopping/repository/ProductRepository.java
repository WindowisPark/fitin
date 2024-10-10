package com.fitin.shopping.repository;

import com.fitin.shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상품명으로 검색하는 메서드
    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> findBestsellers(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> findNewArrivals(Pageable pageable);

    @Query("SELECT p FROM Product p")
    List<Product> findRecommendedProducts(Pageable pageable);

    @Query("SELECT p FROM Product p")
    List<Product> findRecentlyViewedProducts(Pageable pageable);
}