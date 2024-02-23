package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.RestaurantsRequestDto;
import com.sparta.outsourcing.dto.RestaurantsResponseDto;
import com.sparta.outsourcing.entity.Restaurants;
import com.sparta.outsourcing.service.RestaurantsService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestaurantsController {
  private final RestaurantsService restaurantsService;
  @PostMapping("/restaurants")
  public ResponseEntity<RestaurantsResponseDto> createRestaurant(@RequestBody RestaurantsRequestDto requestDto){
    RestaurantsResponseDto restaurantsResponseDto = restaurantsService.createRestaurant(requestDto);

    return ResponseEntity.ok(restaurantsResponseDto);
  }
}
