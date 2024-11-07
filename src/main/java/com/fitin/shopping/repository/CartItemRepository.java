package com.fitin.shopping.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.fitin.shopping.dto.CartDetailDto;
import com.fitin.shopping.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT new com.fitin.shopping.dto.CartDetailDto$CartItemInfo(ci.id, p.name, ci.quantity, p.price) " +
           "FROM CartItem ci JOIN ci.product p WHERE ci.cart.id = :cartId")
    List<CartDetailDto.CartItemInfo> findCartItemInfoList(@Param("cartId") Long cartId);

    int countByCartId(Long cartId);

    List<CartItem> findByCartId(Long cartId);

    @Query("SELECT SUM(ci.quantity * p.price) FROM CartItem ci JOIN ci.product p WHERE ci.cart.id = :cartId")
    BigDecimal calculateCartTotalAmount(@Param("cartId") Long cartId);
}