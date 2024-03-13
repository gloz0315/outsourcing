package com.sparta.outsourcing.querydsltest;

import com.sparta.outsourcing.domain.restaurant.entity.Restaurants;

public class RestaurantInit extends Restaurants {

  public static Restaurants init() {
    return Restaurants.builder()
        .name("테스트 가게")
        .number("010-1111-1111")
        .address("테스트 가게 주소")
        .category("양식")
        .build();
  }
}
