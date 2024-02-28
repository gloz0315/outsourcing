package com.sparta.outsourcing.domain.menu.model;

import jakarta.persistence.EntityNotFoundException;
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

    throw new EntityNotFoundException("해당 음식 종류가 존재하지 않습니다.");
  }
}
