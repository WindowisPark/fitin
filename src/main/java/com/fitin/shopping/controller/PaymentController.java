package com.fitin.shopping.controller;

import com.fitin.shopping.dto.PaymentCreateDto;
import com.fitin.shopping.dto.PaymentResponseDto;
import com.fitin.shopping.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> processPayment(@Valid @RequestBody PaymentCreateDto paymentCreateDto) {
        PaymentResponseDto paymentResponseDto = paymentService.processPayment(paymentCreateDto);
        return ResponseEntity.ok(paymentResponseDto);
    }
}
