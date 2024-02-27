package com.sparta.outsourcing.domain.menu.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class MenuResponseDto {

  private Long id;
  private Long restaurantId;
  private String name;
  private String category;
  private int price;
  private String description;
}
