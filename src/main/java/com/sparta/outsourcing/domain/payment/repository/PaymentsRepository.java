package com.sparta.outsourcing.domain.payment.repository;

import com.sparta.outsourcing.domain.payment.entity.Payments;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository{
  Payments findPaymentsByOrderId(Long orderId);
}
