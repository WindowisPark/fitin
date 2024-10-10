package com.fitin.shopping.controller;

import com.fitin.shopping.dto.OrderCreateDto;
import com.fitin.shopping.dto.OrderHistoryDto;
import com.fitin.shopping.dto.OrderResponseDto;
import com.fitin.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        OrderResponseDto orderResponse = orderService.createOrder(orderCreateDto);
        return ResponseEntity.ok(orderResponse);
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
