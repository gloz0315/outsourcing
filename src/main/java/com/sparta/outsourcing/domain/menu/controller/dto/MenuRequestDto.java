package com.sparta.outsourcing.domain.menu.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuRequestDto {

  private Long restaurantId;

  @Size(max = 20)
  private String name;

  @Size(max = 20)
  private String category;

  private int price;

  private String description;
}
