package com.sparta.outsourcing.favorite.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FavoriteRequestDto {

  private Long memberId;
  private Long restaurantId;

}
