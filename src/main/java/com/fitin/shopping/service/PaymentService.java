package com.fitin.shopping.service;

import com.fitin.shopping.dto.PaymentCreateDto;
import com.fitin.shopping.dto.PaymentResponseDto;
import com.fitin.shopping.entity.Order;
import com.fitin.shopping.entity.Payment;
import com.fitin.shopping.repository.OrderRepository;
import com.fitin.shopping.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public PaymentResponseDto processPayment(PaymentCreateDto paymentCreateDto) {
        Order order = orderRepository.findById(paymentCreateDto.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        Payment payment = new Payment(
            paymentCreateDto.getAmount(),
            paymentCreateDto.getPaymentMethod(),
            order
        );
        
        paymentRepository.save(payment);
        
        return new PaymentResponseDto(
            payment.getId(),
            payment.getAmount(),
            payment.getPaymentMethod(),
            payment.getPaymentDate(),
            payment.getOrder().getId()
        );
    }
}
