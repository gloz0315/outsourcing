package com.sparta.outsourcing.domain.basket.service;

import com.sparta.outsourcing.domain.basket.service.dto.BasketRequestDto;
import com.sparta.outsourcing.domain.basket.service.dto.BasketResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

public interface BasketService {

  // 장바구니 담기
  BasketResponseDto inputBasket(UserDetails userDetails, BasketRequestDto dto);

  // 장바구니 조회
  List<BasketResponseDto> getBasketInfo(UserDetails userDetails);

  Page<BasketResponseDto> getBasketInfo(UserDetails userDetails, int page, int size);

  List<BasketResponseDto> getBasketInfoJpa(UserDetails userDetails, int page, int size);

  // 장바구니 삭제
  void deleteBasket(String email);
}
