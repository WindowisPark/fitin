package com.fitin.shopping.repository;

import com.fitin.shopping.entity.CartItem;
import com.fitin.shopping.dto.CartDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // cartId를 기반으로 장바구니 내 상세 상품 정보를 조회하는 메서드
	@Query("SELECT new com.fitin.shopping.dto.CartDetailDto(ci.id, p.name, ci.quantity, p.price) " +
		       "FROM CartItem ci JOIN ci.product p WHERE ci.cart.id = :cartId")
		List<CartDetailDto> findCartDetailDtoList(@Param("cartId") Long cartId);
	
	 int countByCartId(Long cartId);
}
