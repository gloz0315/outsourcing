package com.sparta.outsourcing.domain.restaurant.service;

import com.sparta.outsourcing.domain.member.model.MemberRole;
import com.sparta.outsourcing.domain.member.repository.member.MemberRepository;
import com.sparta.outsourcing.domain.restaurant.dto.RestaurantsRequestDto;
import com.sparta.outsourcing.domain.restaurant.dto.RestaurantsResponseDto;
import com.sparta.outsourcing.domain.restaurant.entity.Restaurants;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import com.sparta.outsourcing.global.exception.CustomError;
import com.sparta.outsourcing.global.exception.CustomException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantsService {

  private final RestaurantsRepository restaurantsRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public RestaurantsResponseDto createRestaurant(RestaurantsRequestDto restaurantsRequestDto,
      String email) {

    RestaurantsResponseDto restaurantsResponseDto;
    if (memberRepository.findMemberOrElseThrow(email).getRole().equals(MemberRole.ADMIN)) {
      Restaurants restaurants = restaurantsRequestDto.toEntity();
      restaurantsRepository.save(restaurants);
      restaurantsResponseDto = new RestaurantsResponseDto(restaurants);
    } else {
      throw new CustomException(CustomError.NO_AUTH);
    }
    return restaurantsResponseDto;
  }

  public RestaurantsResponseDto getRestaurant(Long restaurantId) {

    Restaurants foundRestaurant = findByRestaurantId(restaurantId);
    RestaurantsResponseDto restaurantsResponseDto = new RestaurantsResponseDto(foundRestaurant);
    return restaurantsResponseDto;
  }


  public List<RestaurantsResponseDto> getRestaurantList() {
    return restaurantsRepository.findAllByOrderByRestaurantId().stream()
        .map(RestaurantsResponseDto::new).toList();

  } // for each문이랑 유사

  @Transactional
  public long deleteRestaurant(Long restaurantId, String email) {
    if (memberRepository.findMemberOrElseThrow(email).getRole().equals(MemberRole.ADMIN)) {
      deleteByRestaurantId(restaurantId);
    } else {
      throw new CustomException(CustomError.NO_AUTH);
    }
    return restaurantId;
  }

  @Transactional
  public RestaurantsResponseDto updateRestaurant(Long restaurantId,
      RestaurantsRequestDto restaurantsRequestDto, String email) {

    if (memberRepository.findMemberOrElseThrow(email).getRole().equals(MemberRole.ADMIN)) {
      Restaurants restaurants = findByRestaurantId(restaurantId);
      restaurants.update(restaurantsRequestDto);
      Restaurants updatedRestaurant = restaurantsRepository.save(restaurants);
      RestaurantsResponseDto restaurantsResponseDto = new RestaurantsResponseDto(updatedRestaurant);
      return restaurantsResponseDto;
    } else {
      throw new CustomException(CustomError.NO_AUTH);
    }

  }

  private void deleteByRestaurantId(Long restaurantId) {
    Optional<Restaurants> restaurantsOptional = restaurantsRepository.findById(restaurantId);

    if (restaurantsOptional.isEmpty()) {
      throw new CustomException(CustomError.RESTAURANT_NOT_EXIST);
    } else {
      restaurantsRepository.deleteById(restaurantId);
    }
  }

  private Restaurants findByRestaurantId(Long restaurantId) {
    return restaurantsRepository.findById(restaurantId)
        .orElseThrow(() -> new CustomException(CustomError.RESTAURANT_NOT_EXIST));
  }
}
