package com.sparta.outsourcing.domain.payment.repository;

import com.sparta.outsourcing.domain.order.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository{
  Order findByOrderId(Long orderId);
}
