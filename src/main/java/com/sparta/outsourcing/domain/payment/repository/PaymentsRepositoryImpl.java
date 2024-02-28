package com.sparta.outsourcing.domain.payment.repository;

import com.sparta.outsourcing.domain.payment.entity.Payments;
import com.sparta.outsourcing.global.exception.CustomError;
import com.sparta.outsourcing.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentsRepositoryImpl implements PaymentsRepository {

  private final PaymentsJpaRepository paymentsJpaRepository;

  @Override
  public Payments findPaymentsByOrderId(Long orderId) {
    return paymentsJpaRepository.findPaymentsByOrderId(orderId)
        .orElseThrow(() -> new CustomException(CustomError.NOT_EXIST_PAYMENT));
  }
}
