package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.RestaurantsRequestDto;
import com.sparta.outsourcing.dto.RestaurantsResponseDto;
import com.sparta.outsourcing.service.RestaurantsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestaurantsController {

  private final RestaurantsService restaurantsService;

  @PostMapping("/restaurants")
  public ResponseEntity<RestaurantsResponseDto> createRestaurant(
      @RequestBody RestaurantsRequestDto requestDto) {
    RestaurantsResponseDto restaurantsResponseDto = restaurantsService.createRestaurant(requestDto);

    return ResponseEntity.ok(restaurantsResponseDto);
  }

  @GetMapping("/restaurants/{restaurantId}")
  public ResponseEntity<RestaurantsResponseDto> getRestaurant(@PathVariable Long restaurantId) {
    RestaurantsResponseDto restaurantsResponseDto = restaurantsService.getRestaurant(restaurantId);
    return ResponseEntity.ok(restaurantsResponseDto);
  }

  @GetMapping("/restaurants")
  public ResponseEntity<List<RestaurantsResponseDto>> getRestaurantList() {
    List<RestaurantsResponseDto> restaurantsResponseDto = restaurantsService.getRestaurantList();
    return ResponseEntity.ok(restaurantsResponseDto);
  }
  @PutMapping("/restaurants/{restaurantId}")
  public ResponseEntity<RestaurantsResponseDto> updateRestaurant(@PathVariable Long restaurantId,
      @RequestBody RestaurantsRequestDto restaurantsRequestDto){
    RestaurantsResponseDto restaurantsResponseDto = restaurantsService.updateRestaurant(restaurantId,restaurantsRequestDto);
    return ResponseEntity.ok(restaurantsResponseDto);
  }

  @DeleteMapping("/restaurants/{restaurantId}")
  public ResponseEntity<String> deleteRestaurant(@PathVariable Long restaurantId) {
    restaurantsService.deleteRestaurant(restaurantId);
    return ResponseEntity.ok().body(("가게가 삭제되었습니다"));
  }

}
