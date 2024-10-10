package com.fitin.shopping.service;

import com.fitin.shopping.dto.OrderCreateDto;
import com.fitin.shopping.dto.OrderHistoryDto;
import com.fitin.shopping.dto.OrderItemDto;
import com.fitin.shopping.dto.OrderResponseDto;
import com.fitin.shopping.entity.Order;
import com.fitin.shopping.entity.OrderItem;
import com.fitin.shopping.entity.Product;
import com.fitin.shopping.exception.OrderNotFoundException;
import com.fitin.shopping.exception.ProductNotFoundException;
import com.fitin.shopping.repository.OrderItemRepository;
import com.fitin.shopping.repository.OrderRepository;
import com.fitin.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderCreateDto orderCreateDto) {
        Order order = new Order();
        order.setStatus("NEW");

        // 주문 아이템 생성 및 저장
        for (OrderItemDto itemDto : orderCreateDto.getOrderItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
            
            // 주문 아이템 생성
            OrderItem orderItem = new OrderItem(order, product, itemDto.getQuantity());

            // OrderItem을 저장
            orderItemRepository.save(orderItem);  // OrderItem을 저장합니다.

            // Order에 OrderItem을 추가합니다.
            order.addOrderItem(orderItem);
        }

        // 최종적으로 Order를 저장합니다.
        orderRepository.save(order);  

        return new OrderResponseDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderHistoryDto> getOrderHistory(Long memberId) {
        List<Order> orders = orderRepository.findByMemberId(memberId);

        return orders.stream()
                .map(order -> new OrderHistoryDto(order))
                .collect(Collectors.toList());
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
