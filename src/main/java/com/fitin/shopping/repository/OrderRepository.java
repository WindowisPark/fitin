package com.fitin.shopping.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fitin.shopping.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 특정 사용자 ID로 주문 조회
    @Query("SELECT o FROM Order o WHERE o.member.id = :memberId")
    List<Order> findByMemberId(Long memberId);

    // 주문 목록 조회 (모든 주문 조회)
    @Query("select o from Order o order by o.orderDate desc")
    List<Order> findOrders(Pageable pageable);

    // 모든 주문 개수 조회
    @Query("select count(o) from Order o")
    Long countAllOrders();
    
    List<Order> findByMemberIdOrderByOrderDateDesc(Long memberId);
}
