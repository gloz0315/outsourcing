package com.sparta.outsourcing.domain.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {

  private String contents;
  private int score;
  private Long memberEntityId;
  private Long restaurantId;


}
