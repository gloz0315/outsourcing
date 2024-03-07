package com.sparta.outsourcing.domain.menu.service;

import com.sparta.outsourcing.domain.menu.controller.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.controller.dto.MenuUpdateRequestDto;
import com.sparta.outsourcing.domain.menu.service.dto.MenuResponseDto;
import java.util.List;

public interface MenuService {

  // 메뉴 생성
  MenuResponseDto createMenu(String email, MenuRequestDto dto);

  // restaurantId를 통한 메뉴들 조회
  List<MenuResponseDto> getMenus(Long restaurantId);

  // restaurantId와 menuId를 통한 메뉴의 상세 조회
  MenuResponseDto getMenu(Long restaurantId, Long menuId);

  // email을 통해 접근 권한자인지 확인 후, 메뉴를 수정
  MenuResponseDto updateMenu(String email, Long restaurantId, Long menuId,
      MenuUpdateRequestDto dto);

  // 메뉴 삭제
  void deleteMenu(String email, Long restaurantId, Long menuId);
}
