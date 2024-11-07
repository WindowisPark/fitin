package com.fitin.shopping.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.auth.entity.Member;
import com.fitin.auth.repository.MemberRepository;
import com.fitin.shopping.dto.OrderCreateDto;
import com.fitin.shopping.dto.OrderHistoryDto;
import com.fitin.shopping.dto.OrderItemDto;
import com.fitin.shopping.dto.OrderResponseDto;
import com.fitin.shopping.entity.Order;
import com.fitin.shopping.entity.OrderItem;
import com.fitin.shopping.entity.Product;
import com.fitin.shopping.exception.MemberNotFoundException;
import com.fitin.shopping.exception.OrderNotFoundException;
import com.fitin.shopping.repository.OrderRepository;
import com.fitin.shopping.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;  // 추가

    public OrderService(
        OrderRepository orderRepository,
        MemberRepository memberRepository,
        ProductRepository productRepository) {  // 생성자 주입
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;  // 초기화
    }


    @Transactional
    public OrderResponseDto createOrder(OrderCreateDto orderCreateDto) {
        // 회원 조회
        Member member = memberRepository.findById(orderCreateDto.getMemberId())
            .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + orderCreateDto.getMemberId()));

        // 주문 생성
        Order order = new Order();
        order.setMember(member);
        order.setPaymentMethod(orderCreateDto.getPaymentMethod());
        order.setShippingAddress(orderCreateDto.getShippingAddress());

        // OrderItems 처리
        if (orderCreateDto.getOrderItems() != null && !orderCreateDto.getOrderItems().isEmpty()) {
            for (OrderItemDto itemDto : orderCreateDto.getOrderItems()) {
                Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + itemDto.getProductId()));
                
                // OrderItem 생성 시 생성자 사용
                OrderItem orderItem = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
                order.addOrderItem(orderItem);
            }
        }

        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDto(savedOrder);
    }
    
    @Transactional(readOnly = true)
    public List<OrderHistoryDto> getOrderHistory(Long memberId) {
        List<Order> orders = orderRepository.findByMemberIdOrderByOrderDateDesc(memberId);
        return orders.stream()
                .map(this::convertToOrderHistoryDto)
                .collect(Collectors.toList());
    }
    
    private OrderHistoryDto convertToOrderHistoryDto(Order order) {
        return new OrderHistoryDto(order);
    }
    
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return new OrderResponseDto(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> new OrderResponseDto(order))
                .collect(Collectors.toList());
    }
}
