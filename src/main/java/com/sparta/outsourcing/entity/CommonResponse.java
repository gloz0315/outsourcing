package com.sparta.outsourcing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
  private int code;
  private String message;
  private T data;
}
