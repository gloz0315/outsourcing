package com.sparta.outsourcing.domain.payment.repository;

import com.sparta.outsourcing.domain.order.repository.OrderJpaRepository;
import com.sparta.outsourcing.domain.payment.entity.Payments;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentsRepositoryImpl implements PaymentsRepository {

  private final PaymentsJpaRepository paymentsJpaRepository;

  @Override
  public Payments findPaymentsByOrderId(Long orderId) {
   return paymentsJpaRepository.findPaymentsByOrderId(orderId)
        .orElseThrow(() -> new EntityNotFoundException("결제 정보를 찾을 수 없습니다"));
  }

}