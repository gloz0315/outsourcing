package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.RestaurantsRequestDto;
import com.sparta.outsourcing.dto.RestaurantsResponseDto;
import com.sparta.outsourcing.entity.Restaurants;
import com.sparta.outsourcing.repository.RestaurantsRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class RestaurantsService {
  private final RestaurantsRepository restaurantsRepository;
  @Transactional
  public RestaurantsResponseDto createRestaurant(RestaurantsRequestDto requestDto) {

    Restaurants restaurants = requestDto.toEntity();
    restaurantsRepository.save(restaurants);
    RestaurantsResponseDto responseDto = new RestaurantsResponseDto(restaurants);

    return responseDto;
  }
}
