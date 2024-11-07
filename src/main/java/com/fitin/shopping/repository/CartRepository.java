package com.fitin.shopping.repository;

import com.fitin.auth.entity.Member;
import com.fitin.shopping.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
    Optional<Cart> findByMember(Member member);
    Optional<Cart> findByMember_Id(Long memberId);
    
}