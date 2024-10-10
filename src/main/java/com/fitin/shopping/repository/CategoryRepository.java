package com.fitin.shopping.repository;

import com.fitin.shopping.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // 이름으로 카테고리 찾기
    Category findByName(String name);
    
    // 이름에 특정 문자열이 포함된 카테고리 목록 찾기
    List<Category> findByNameContaining(String keyword);
    
    // 이름으로 정렬된 모든 카테고리 가져오기
    List<Category> findAllByOrderByNameAsc();
    
    // 특정 제품과 연관된 카테고리 찾기
    // List<Category> findByProductsId(Long productId);
    // 주석 처리한 이유: @ManyToMany 관계에서는 이 쿼리가 효율적이지 않을 수 있음.
    // 대신 Product 엔티티에서 카테고리를 조회하는 것이 더 효율적일 수 있음.
}