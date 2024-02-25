package com.sparta.outsourcing.restaurant.repository;

import com.sparta.outsourcing.restaurant.entity.Restaurants;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsRepository extends JpaRepository<Restaurants,Long> {
  List<Restaurants> findAllByOrderByRestaurantId();
}
