package com.sparta.outsourcing.domain.menu.service;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.MemberRole;
import com.sparta.outsourcing.domain.member.repository.member.MemberRepository;
import com.sparta.outsourcing.domain.menu.controller.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.controller.dto.MenuUpdateRequestDto;
import com.sparta.outsourcing.domain.menu.model.Menu;
import com.sparta.outsourcing.domain.menu.model.MenuType;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.menu.service.dto.MenuResponseDto;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

  private final MenuRepository menuRepository;
  private final MemberRepository memberRepository;
  private final RestaurantsRepository restaurantsRepository;

  public MenuResponseDto createMenu(String email, MenuRequestDto dto) {
    Member member = memberRepository.findMemberOrElseThrow(email);

    MenuType.checkMenuType(dto.getCategory());

    if (member.getRole().equals(MemberRole.USER)) {
      throw new IllegalArgumentException("접근 권한이 없습니다.");
    }

    if (restaurantsRepository.findById(dto.getRestaurantId()).isEmpty()) {
      throw new EntityNotFoundException("해당 가게는 존재하지 않습니다.");
    }

    if (menuRepository.findByRestaurantIdAndName(dto.getRestaurantId(), dto.getName())) {
      throw new IllegalArgumentException("이미 해당 음식점에 음식이 존재합니다.");
    }

    Menu menu = menuRepository.createMenu(dto);

    return MenuResponseDto.builder()
        .id(menu.getId())
        .restaurantId(menu.getRestaurantId())
        .name(menu.getName())
        .price(menu.getPrice())
        .description(menu.getDescription())
        .category(menu.getCategory())
        .build();
  }

  @Transactional(readOnly = true)
  public List<MenuResponseDto> getMenus(Long restaurantId) {
    if (restaurantsRepository.findById(restaurantId).isEmpty()) {
      throw new EntityNotFoundException("해당 가게는 존재하지 않습니다.");
    }

    List<Menu> menuList = menuRepository.findByRestaurantId(restaurantId);

    return menuList.stream().map(
        menu -> MenuResponseDto.builder()
            .id(menu.getId())
            .name(menu.getName())
            .category(menu.getCategory())
            .price(menu.getPrice())
            .description(menu.getDescription())
            .build()
    ).toList();
  }

  @Transactional(readOnly = true)
  public MenuResponseDto getMenu(Long restaurantId, Long menuId) {
    if (restaurantsRepository.findById(restaurantId).isEmpty()) {
      throw new EntityNotFoundException("해당 가게는 존재하지 않습니다.");
    }

    Menu menu = menuRepository.findByMenu(restaurantId, menuId);

    return MenuResponseDto.builder()
        .id(menu.getId())
        .name(menu.getName())
        .category(menu.getCategory())
        .price(menu.getPrice())
        .description(menu.getDescription())
        .build();
  }

  public MenuResponseDto updateMenu(String email, Long restaurantId, Long menuId,
      MenuUpdateRequestDto dto) {
    Member member = memberRepository.findMemberOrElseThrow(email);

    MenuType.checkMenuType(dto.getCategory());

    if (member.getRole().equals(MemberRole.USER)) {
      throw new IllegalArgumentException("접근 권한이 없습니다.");
    }

    if (restaurantsRepository.findById(restaurantId).isEmpty()) {
      throw new EntityNotFoundException("해당 가게는 존재하지 않습니다.");
    }

    Menu menu = menuRepository.updateMenu(dto, menuId);

    return MenuResponseDto.builder()
        .id(menu.getId())
        .restaurantId(menu.getRestaurantId())
        .name(menu.getName())
        .price(menu.getPrice())
        .category(menu.getCategory())
        .description(menu.getDescription())
        .build();
  }

  public void deleteMenu(String email, Long restaurantId, Long menuId) {
    Member member = memberRepository.findMemberOrElseThrow(email);

    if (member.getRole().equals(MemberRole.USER)) {
      throw new IllegalArgumentException("접근 권한이 없습니다.");
    }

    menuRepository.deleteMenu(restaurantId, menuId);
  }
}
