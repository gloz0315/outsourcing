package com.sparta.outsourcing.domain.menu.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Menu {

  private Long id;
  private Long restaurantId;
  private String name;
  private String category;
  private int price;
  private String description;
}
