package com.sparta.outsourcing.global.exception;

import com.sparta.outsourcing.global.dto.CommonResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomJwtException.class)
  public ResponseEntity<CommonResponseDto<String>> jwtExceptionHandler(Exception e) {
    return CommonResponseDto.unauthorizedRequest(e.getMessage());
  }

}
