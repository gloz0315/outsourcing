package com.sparta.outsourcing.member.test;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.MemberRole;

public interface MemberTest {

  Long TEST_MEMBER_ID = 1L;
  String TEST_MEMBER_EMAIL = "test123@naver.com";
  String TEST_NAME = "Test12";
  String TEST_PASSWORD = "Sk@123456";
  String TEST_NUMBER = "010-1111-1111";
  String TEST_ADDRESS = "테스트주소-테스트주소1";

  Member TEST_MEMBER = Member.builder()
      .id(TEST_MEMBER_ID)
      .email(TEST_MEMBER_EMAIL)
      .role(MemberRole.USER)
      .address(TEST_ADDRESS)
      .number(TEST_NUMBER)
      .nickname(TEST_NAME)
      .build();

}
