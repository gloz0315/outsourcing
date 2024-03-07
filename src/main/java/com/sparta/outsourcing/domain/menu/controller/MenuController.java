package com.sparta.outsourcing.domain.menu.controller;

import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_CREATE_MENU;
import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_DELETE_MENU;
import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_SEARCH_MENU;
import static com.sparta.outsourcing.global.success.SuccessCode.SUCCESS_UPDATE_MENU;

import com.sparta.outsourcing.domain.menu.controller.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.controller.dto.MenuUpdateRequestDto;
import com.sparta.outsourcing.domain.menu.service.MenuServiceImpl;
import com.sparta.outsourcing.domain.menu.service.dto.MenuResponseDto;
import com.sparta.outsourcing.global.dto.CommonResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

  private final MenuServiceImpl menuService;

  @PostMapping
  public ResponseEntity<CommonResponseDto<MenuResponseDto>> createMenu(
      @AuthenticationPrincipal UserDetails userDetails,
      @Valid @RequestBody MenuRequestDto dto
  ) {
    MenuResponseDto responseDto = menuService.createMenu(userDetails.getUsername(), dto);
    return CommonResponseDto.ok(SUCCESS_CREATE_MENU, responseDto);
  }

  @GetMapping("/{restaurantId}")
  public ResponseEntity<CommonResponseDto<List<MenuResponseDto>>> getMenus(
      @PathVariable("restaurantId") Long restaurantId
  ) {
    List<MenuResponseDto> responseDtoList = menuService.getMenus(restaurantId);
    return CommonResponseDto.ok(SUCCESS_SEARCH_MENU, responseDtoList);
  }

  @GetMapping("/{restaurantId}/{menuId}")
  public ResponseEntity<CommonResponseDto<MenuResponseDto>> getMenu(
      @PathVariable("restaurantId") Long restaurantId,
      @PathVariable("menuId") Long menuId
  ) {
    MenuResponseDto responseDto = menuService.getMenu(restaurantId, menuId);
    return CommonResponseDto.ok(SUCCESS_SEARCH_MENU, responseDto);
  }

  @PutMapping("/{restaurantId}/{menuId}")
  public ResponseEntity<CommonResponseDto<MenuResponseDto>> updateMenu(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable("restaurantId") Long restaurantId,
      @PathVariable("menuId") Long menuId,
      @Validated @RequestBody MenuUpdateRequestDto dto
  ) {
    MenuResponseDto responseDto = menuService.updateMenu(userDetails.getUsername(), restaurantId,
        menuId, dto);
    return CommonResponseDto.ok(SUCCESS_UPDATE_MENU, responseDto);
  }

  @DeleteMapping("/{restaurantId}/{menuId}")
  public ResponseEntity<CommonResponseDto<Void>> deleteMenu(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable("restaurantId") Long restaurantId,
      @PathVariable("menuId") Long menuId
  ) {
    menuService.deleteMenu(userDetails.getUsername(), restaurantId, menuId);
    return CommonResponseDto.ok(SUCCESS_DELETE_MENU, null);
  }
}
