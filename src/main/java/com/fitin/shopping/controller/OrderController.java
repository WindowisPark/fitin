package com.fitin.shopping.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitin.auth.entity.Member;
import com.fitin.auth.repository.MemberRepository;
import com.fitin.shopping.dto.OrderCreateDto;
import com.fitin.shopping.dto.OrderHistoryDto;
import com.fitin.shopping.dto.OrderResponseDto;
import com.fitin.shopping.exception.MemberNotFoundException;
import com.fitin.shopping.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberRepository memberRepository;
    
    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderCreateDto orderCreateDto, 
            Authentication authentication) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // email로 회원 찾기
        Member member = memberRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new MemberNotFoundException("Member not found with email: " + userDetails.getUsername()));
        
        // member 정보를 orderCreateDto에 설정
        orderCreateDto.setMemberId(member.getId());
        
        OrderResponseDto orderResponse = orderService.createOrder(orderCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
    
    // 주문 내역 조회
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<OrderHistoryDto>> getOrderHistory(@PathVariable("userId")Long userId) {
        List<OrderHistoryDto> orderHistory = orderService.getOrderHistory(userId);
        return ResponseEntity.ok(orderHistory);
    }
    
    // 특정 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable("orderId") Long orderId) {
        OrderResponseDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    // 모든 주문 조회
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    // 주문 취소
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
