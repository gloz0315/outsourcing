package com.sparta.outsourcing.global.exception;

import lombok.Getter;

@Getter
public enum CustomError {
  // 회원에 대한 오류
  NO_AUTH("해당 유저의 권한이 없습니다."),
  MEMBER_EXISTS("해당 유저가 존재합니다."),
  MEMBER_NOT_EXISTS("해당 유저가 존재하지 않습니다."),
  PASSWORD_EQUALS("현재 비밀번호입니다."),
  PASSWORD_ERROR("비밀번호가 일치하지 않습니다."),
  CHANGE_PASSWORD_ERROR("변경할 비밀번호가 서로 일치하지 않습니다."),
  CURRENT_PASSWORD_ERROR("최근에 해당 비밀번호로 변경한 적이 있습니다."),

  // 가게에 대한 오류
  RESTAURANT_NOT_EXIST("가게가 존재하지 않습니다."),

  // 장바구니에 대한 오류
  EMPTY_BASKET("장바구니가 비어있습니다."),
  NOT_CONTAIN_MENU("장바구니에 담을 수 없습니다. 한 가게에서만 담을 수 있습니다."),

  // 주문에 대한 오류
  NOT_CANCEL_ORDER("현재 배달중이므로 결제를 취소할 수 없습니다"),
  NOT_EXIST_ORDER("주문 정보가 존재하지 않습니다."),

  // 리뷰에 대한 오류
  NOT_EXIST_REVIEW("해당 리뷰가 존재하지 않습니다."),

  // 좋아요에 대한 오류
  ALREADY_REGISTER_FAVORITE("이미 좋아요를 누른 가게입니다."),
  NOT_EXIST_FAVORITE("좋아요를 누른 가게를 찾을 수 없습니다."),

  // 메뉴에 대한 오류
  NOT_EXIST_MENU_TYPE("음식의 종류가 존재하지 않습니다."),
  NOT_EXIST_MENU("음식이 존재하지 않습니다."),
  ALREADY_EXIST_MENU("해당 음식이 존재합니다."),


  ;

  private final String message;

  CustomError(String message) {
    this.message = message;
  }
}
