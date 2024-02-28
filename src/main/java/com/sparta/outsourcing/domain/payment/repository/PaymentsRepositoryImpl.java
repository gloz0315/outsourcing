package com.sparta.outsourcing.domain.payment.repository;

import com.sparta.outsourcing.domain.order.repository.OrderJpaRepository;
import com.sparta.outsourcing.domain.payment.entity.Payments;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentsRepositoryImpl implements PaymentsRepository{

  private final PaymentsJpaRepository paymentsJpaRepository;
  @Override
  public Payments findPaymentsByOrderId(Long orderId) {
    return paymentsJpaRepository.findById(orderId).orElseThrow(
        () -> new EntityNotFoundException("해당 주문번호가 존재하지 않습니다")
    );
}


}
