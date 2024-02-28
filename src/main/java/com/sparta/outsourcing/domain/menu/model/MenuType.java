package com.sparta.outsourcing.domain.menu.model;

import static com.sparta.outsourcing.global.exception.CustomError.NOT_EXIST_MENU_TYPE;

import com.sparta.outsourcing.global.exception.CustomException;
import lombok.Getter;

@Getter
public enum MenuType {
  KOREAN("한식"),
  JAPANESE("일식"),
  CHINESE("중식"),
  WESTERN("양식");

  private final String name;

  MenuType(String name) {
    this.name = name;
  }

  public static void checkMenuType(String type) {
    for (MenuType menuType : MenuType.values()) {
      if (menuType.name.equals(type)) {
        return;
      }
    }

    throw new CustomException(NOT_EXIST_MENU_TYPE);
  }
}
