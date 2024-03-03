package com.sparta.outsourcing.member.memberInit;

import static com.sparta.outsourcing.member.test.MemberInfo.TEST_ADDRESS;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_EMAIL;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_ID;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_NAME;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_NUMBER;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_PASSWORD;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.MemberRole;

public class MemberInit extends Member {

  public Member init() {
    return Member.builder()
        .id(TEST_ID)
        .email(TEST_EMAIL)
        .nickname(TEST_NAME)
        .role(MemberRole.USER)
        .address(TEST_ADDRESS)
        .number(TEST_NUMBER)
        .password(TEST_PASSWORD)
        .build();
  }
}
