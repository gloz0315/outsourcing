package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.Restaurants;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class RestaurantsResponseDto {
  private Long restaurant_id;
  private String name;
  private String category;
  private String address;
  private String number;
  private LocalDate created_date;
  private LocalDate updated_date;




  public RestaurantsResponseDto (Restaurants restaurants) {
    this.restaurant_id = restaurants.getResturant_id();
    this.name = restaurants.getName();
    this.category = restaurants.getCategory();
    this.address = restaurants.getAddress();
    this.number = restaurants.getNumber();
    this.created_date = restaurants.getCreated_date();
    this.updated_date = restaurants.getUpdated_date();
  }
}
