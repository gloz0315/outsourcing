package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.RestaurantsRequestDto;
import com.sparta.outsourcing.dto.RestaurantsResponseDto;
import com.sparta.outsourcing.entity.Restaurants;
import com.sparta.outsourcing.repository.RestaurantsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantsService {

  private final RestaurantsRepository restaurantsRepository;

  @Transactional
  public RestaurantsResponseDto createRestaurant(RestaurantsRequestDto restaurantsRequestDto) {

    Restaurants restaurants = restaurantsRequestDto.toEntity();
    restaurantsRepository.save(restaurants);
    RestaurantsResponseDto restaurantsResponseDto = new RestaurantsResponseDto(restaurants);

    return restaurantsResponseDto;
  }

  public RestaurantsResponseDto getRestaurant(Long restaurantId) {
    Restaurants foundRestaurant = findByRestaurantId(restaurantId);
    RestaurantsResponseDto restaurantsResponseDto = new RestaurantsResponseDto(foundRestaurant);
    return restaurantsResponseDto;
  }


  private Restaurants findByRestaurantId(Long restaurantId) {
    return restaurantsRepository.findById(restaurantId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다"));
  }

  public List<RestaurantsResponseDto> getRestaurantList(){
    return restaurantsRepository.findAll().stream().map(RestaurantsResponseDto::new).toList();
  }

}
