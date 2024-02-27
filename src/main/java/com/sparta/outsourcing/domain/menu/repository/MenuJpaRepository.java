package com.sparta.outsourcing.domain.menu.repository;

import com.sparta.outsourcing.domain.menu.model.entity.MenuEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuJpaRepository extends JpaRepository<MenuEntity, Long> {

  Optional<MenuEntity> findByRestaurantIdAndName(Long restaurantId, String name);

  List<MenuEntity> findByRestaurantId(Long restaurantId);

  Optional<MenuEntity> findByRestaurantIdAndId(Long restaurantId, Long id);
}
