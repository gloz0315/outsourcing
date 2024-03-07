package com.sparta.outsourcing.domain.menu.repository.querydsl;

import com.sparta.outsourcing.domain.menu.model.entity.MenuEntity;
import java.util.List;
import java.util.Optional;

public interface MenuRepositoryInformation {

  List<MenuEntity> findByRestaurantId(Long restaurantId);

  Optional<MenuEntity> findByRestaurantIdAndId(Long restaurantId, Long id);

  Optional<MenuEntity> findByRestaurantIdAndName(Long restaurantId, String name);
}
