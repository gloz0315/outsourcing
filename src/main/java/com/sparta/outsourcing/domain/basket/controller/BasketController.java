package com.sparta.outsourcing.domain.basket.controller;

import com.sparta.outsourcing.domain.basket.service.BasketService;
import com.sparta.outsourcing.domain.basket.service.dto.BasketRequestDto;
import com.sparta.outsourcing.domain.basket.service.dto.BasketResponseDto;
import com.sparta.outsourcing.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {

  private final BasketService basketService;

  @PostMapping
  public ResponseEntity<CommonResponseDto<BasketResponseDto>> inputBasket(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody BasketRequestDto dto
  ) {
    BasketResponseDto responseDto = basketService.inputBasket(userDetails, dto);
    return CommonResponseDto.ok("장바구니에 담겨졌습니다.", responseDto);
  }
}
