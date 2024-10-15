package com.fitin.shopping.controller;

import com.fitin.auth.entity.Member;
import com.fitin.auth.repository.MemberRepository;
import com.fitin.shopping.exception.MemberNotFoundException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.fitin.shopping.dto.OrderCreateDto;
import com.fitin.shopping.dto.OrderHistoryDto;
import com.fitin.shopping.dto.OrderResponseDto;
import com.fitin.shopping.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberRepository memberRepository;
    
    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = authentication.getName();
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findById(Long.parseLong(userDetails.getUsername()))
            .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + userDetails.getUsername()));
        
       
        OrderResponseDto orderResponse = orderService.createOrder(orderCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    // 주문 내역 조회
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<OrderHistoryDto>> getOrderHistory(@PathVariable Long userId) {
        List<OrderHistoryDto> orderHistory = orderService.getOrderHistory(userId);
        return ResponseEntity.ok(orderHistory);
    }

    // 주문 취소
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
