package com.sparta.outsourcing.domain.payment.repository;

import com.sparta.outsourcing.domain.payment.entity.Payments;


public interface PaymentsRepository{
  Payments findPaymentsByOrderId(Long orderId);
}
