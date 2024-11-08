package com.fitin.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fitin.shopping.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 필요한 추가 쿼리 메서드가 있을 경우 정의합니다.
    // 예: 특정 조건에 따라 OrderItem을 조회하는 메서드
}
