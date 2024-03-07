package com.sparta.outsourcing.domain.basket.service;

import com.sparta.outsourcing.domain.basket.service.dto.BasketRequestDto;
import com.sparta.outsourcing.domain.basket.service.dto.BasketResponseDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

public interface BasketService {

  // 장바구니 담기
  BasketResponseDto inputBasket(UserDetails userDetails, BasketRequestDto dto);

  // 장바구니 조회
  List<BasketResponseDto> getBasketInfo(UserDetails userDetails);

  // 장바구니 삭제
  void deleteBasket(String email);
}
