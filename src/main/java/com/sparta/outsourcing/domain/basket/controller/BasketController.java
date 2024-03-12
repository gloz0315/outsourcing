package com.sparta.outsourcing.domain.basket.controller;

import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_CONTAIN;
import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_DELETE_BASKET;
import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_SEARCH_BASKET;

import com.sparta.outsourcing.domain.basket.service.BasketServiceImpl;
import com.sparta.outsourcing.domain.basket.service.dto.BasketRequestDto;
import com.sparta.outsourcing.domain.basket.service.dto.BasketResponseDto;
import com.sparta.outsourcing.global.dto.CommonResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {

  private final BasketServiceImpl basketService;

  @PostMapping
  public ResponseEntity<CommonResponseDto<BasketResponseDto>> inputBasket(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody BasketRequestDto dto
  ) {
    BasketResponseDto responseDto = basketService.inputBasket(userDetails, dto);
    return CommonResponseDto.ok(SUCCESS_CONTAIN, responseDto);
  }

  @GetMapping
  public ResponseEntity<CommonResponseDto<List<BasketResponseDto>>> getBasketInfo(
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    List<BasketResponseDto> responseDtoList = basketService.getBasketInfo(userDetails);
    return CommonResponseDto.ok(SUCCESS_SEARCH_BASKET, responseDtoList);
  }

  @GetMapping("/pageable")
  public ResponseEntity<CommonResponseDto<Page<BasketResponseDto>>> getBasketInfoPage(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam("page") int page, @RequestParam("size") int size
  ) {
    Page<BasketResponseDto> responseDtoPage = basketService.getBasketInfo(userDetails, page, size);
    return CommonResponseDto.ok(SUCCESS_SEARCH_BASKET, responseDtoPage);
  }

  @GetMapping("/jpa")
  public ResponseEntity<CommonResponseDto<List<BasketResponseDto>>> getBasketInfoJpa(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam("page") int page, @RequestParam("size") int size
  ) {
    List<BasketResponseDto> responseDtoPage = basketService.getBasketInfoJpa(userDetails, page,
        size);
    return CommonResponseDto.ok(SUCCESS_SEARCH_BASKET, responseDtoPage);
  }

  @DeleteMapping
  public ResponseEntity<CommonResponseDto<Void>> deleteBasket(
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    basketService.deleteBasket(userDetails.getUsername());
    return CommonResponseDto.ok(SUCCESS_DELETE_BASKET, null);
  }
}
