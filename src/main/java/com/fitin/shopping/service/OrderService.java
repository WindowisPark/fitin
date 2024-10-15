package com.fitin.shopping.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.auth.entity.Member;
import com.fitin.auth.repository.MemberRepository;
import com.fitin.shopping.dto.OrderCreateDto;
import com.fitin.shopping.dto.OrderHistoryDto;
import com.fitin.shopping.dto.OrderResponseDto;
import com.fitin.shopping.entity.Order;
import com.fitin.shopping.exception.MemberNotFoundException;
import com.fitin.shopping.exception.OrderNotFoundException;
import com.fitin.shopping.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;  //

    @Transactional
    public OrderResponseDto createOrder(OrderCreateDto orderCreateDto) {
        Member member = memberRepository.findById(orderCreateDto.getMemberId())
            .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + orderCreateDto.getMemberId()));


        Order order = new Order();
        order.setMember(member);
        order.setPaymentMethod(orderCreateDto.getPaymentMethod());
        order.setShippingAddress(orderCreateDto.getShippingAddress());


        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDto(savedOrder);
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
