package com.sparta.outsourcing.domain.menu.repository;

import com.sparta.outsourcing.domain.menu.controller.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.controller.dto.MenuUpdateRequestDto;
import com.sparta.outsourcing.domain.menu.model.Menu;
import java.util.List;

public interface MenuRepository {

  void deleteMenu(Long restaurantId, Long id);

  boolean findByRestaurantIdAndName(Long restaurantId, String name);

  Menu createMenu(MenuRequestDto dto);

  List<Menu> findByRestaurantId(Long restaurantId);

  Menu findByMenu(Long restaurantId, Long menuId);

  Menu updateMenu(MenuUpdateRequestDto dto, Long id);
}
