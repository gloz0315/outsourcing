package com.sparta.outsourcing.domain.order.service;

import com.sparta.outsourcing.domain.order.service.dto.OrderInfoResponse;
import com.sparta.outsourcing.domain.order.service.dto.OrderResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface OrderService {

  // 주문하기
  OrderResponseDto order(UserDetails userDetails);

  // 주문 정보 조회
  OrderInfoResponse orderInfo(Long orderId);

  // 주문 삭제
  void orderDelete(Long orderId);
}
