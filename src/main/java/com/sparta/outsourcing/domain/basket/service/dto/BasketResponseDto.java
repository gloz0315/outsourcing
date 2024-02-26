package com.sparta.outsourcing.domain.basket.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasketResponseDto {
  private Long memberId;
  private Long restaurantId;
  private Long menuId;
  private int count;
}
