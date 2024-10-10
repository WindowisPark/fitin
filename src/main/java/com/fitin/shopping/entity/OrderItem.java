package com.fitin.shopping.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private BigDecimal price;

    // 주문 항목의 총 가격 계산
    public BigDecimal calculateTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));  // * 대신 multiply 메서드 사용
    }

    // 상품을 주문 항목에 추가하는 메서드
    public void setProduct(Product product, int quantity) {
        this.product = product;
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    // 생성자
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.price = product.getPrice();   // BigDecimal 타입 가격 설정
        this.quantity = quantity;
    }
    public OrderItem(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }
}
