package com.sparta.outsourcing.member.init;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.MemberRole;
import org.springframework.security.crypto.password.PasswordEncoder;

public class InitMember extends Member {

  private PasswordEncoder passwordEncoder;
  public InitMember() {
    super(
        1L,
        "test123@gmail.com",
        MemberRole.USER,
        "TestName",
        "Test123456",
        "테스트주소",
        "010-1111-1111"
    );
  }
}
