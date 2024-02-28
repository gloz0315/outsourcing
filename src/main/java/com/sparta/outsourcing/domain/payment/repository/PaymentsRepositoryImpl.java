package com.sparta.outsourcing.domain.payment.repository;

import com.sparta.outsourcing.domain.order.model.Order;
import com.sparta.outsourcing.domain.order.repository.OrderJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentsRepositoryImpl implements PaymentsRepository{

  private final OrderJpaRepository orderJpaRepository;
  @Override
  public Order findByOrderId(Long orderId) {
    return orderJpaRepository.findById(orderId).orElseThrow(
        () -> new EntityNotFoundException("해당 주문번호가 존재하지 않습니다.")
    ).toModel();
  }
}
