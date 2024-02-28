package com.sparta.outsourcing.global.exception;

import lombok.Getter;

@Getter
public enum CustomError {
  ;

  private final String message;

  CustomError(String message) {
    this.message = message;
  }
}
