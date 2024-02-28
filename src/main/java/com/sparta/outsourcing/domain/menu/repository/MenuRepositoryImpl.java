package com.sparta.outsourcing.domain.menu.repository;

import static com.sparta.outsourcing.global.exception.CustomError.NOT_EXIST_MENU;

import com.sparta.outsourcing.domain.menu.controller.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.controller.dto.MenuUpdateRequestDto;
import com.sparta.outsourcing.domain.menu.model.Menu;
import com.sparta.outsourcing.domain.menu.model.entity.MenuEntity;
import com.sparta.outsourcing.global.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

  private final MenuJpaRepository menuJpaRepository;

  @Override
  public void deleteMenu(Long restaurantId, Long id) {
    MenuEntity menuEntity = menuJpaRepository.findByRestaurantIdAndId(restaurantId, id).
        orElseThrow(() -> new CustomException(NOT_EXIST_MENU));

    menuJpaRepository.delete(menuEntity);
  }

  @Override
  public boolean findByRestaurantIdAndName(Long restaurantId, String name) {
    return menuJpaRepository.findByRestaurantIdAndName(restaurantId, name).isPresent();
  }

  @Override
  public Menu createMenu(MenuRequestDto dto) {
    MenuEntity menuEntity = MenuEntity.of(dto.getRestaurantId(), dto.getName(), dto.getCategory(),
        dto.getPrice(), dto.getDescription());

    menuJpaRepository.save(menuEntity);

    return menuEntity.toModel();
  }

  @Override
  public List<Menu> findByRestaurantId(Long restaurantId) {
    return menuJpaRepository.findByRestaurantId(restaurantId)
        .stream().map(MenuEntity::toModel).toList();
  }

  @Override
  public Menu findByMenu(Long restaurantId, Long menuId) {
    return menuJpaRepository.findByRestaurantIdAndId(restaurantId, menuId).orElseThrow(
        () -> new CustomException(NOT_EXIST_MENU)
    ).toModel();
  }

  @Override
  public Menu updateMenu(MenuUpdateRequestDto dto, Long id) {
    MenuEntity menuEntity = menuJpaRepository.findById(id).orElseThrow(
        () -> new CustomException(NOT_EXIST_MENU)
    );

    menuEntity.updateMenu(dto);

    return menuEntity.toModel();
  }
}
