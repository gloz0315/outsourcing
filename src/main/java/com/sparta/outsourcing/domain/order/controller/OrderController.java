package com.sparta.outsourcing.domain.order.controller;

import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_CANCEL;
import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_ORDER;
import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_SEARCH_ORDER;

import com.sparta.outsourcing.domain.order.service.OrderService;
import com.sparta.outsourcing.domain.order.service.dto.OrderInfoResponse;
import com.sparta.outsourcing.domain.order.service.dto.OrderResponseDto;
import com.sparta.outsourcing.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orderDetails")
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/{orderId}")
  public ResponseEntity<CommonResponseDto<OrderInfoResponse>> orderInfo(
      @PathVariable("orderId") Long orderId
  ) {
    OrderInfoResponse orderInfoResponse = orderService.orderInfo(orderId);
    return CommonResponseDto.ok(SUCCESS_SEARCH_ORDER, orderInfoResponse);
  }

  @PostMapping
  public ResponseEntity<CommonResponseDto<OrderResponseDto>> order(
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    OrderResponseDto orderResponseDto = orderService.order(userDetails);
    return CommonResponseDto.ok(SUCCESS_ORDER, orderResponseDto);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<CommonResponseDto<String>> orderDelete(
      @PathVariable("orderId") Long orderId
  ) {
    orderService.orderDelete(orderId);
    return CommonResponseDto.ok(SUCCESS_CANCEL, null);
  }
}
