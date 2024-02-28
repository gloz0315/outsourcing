package com.sparta.outsourcing.domain.menu.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuUpdateRequestDto {

  @Size(max = 20)
  private String name;

  private Integer price;

  @Size(max = 20)
  private String category;

  private String description;
}
