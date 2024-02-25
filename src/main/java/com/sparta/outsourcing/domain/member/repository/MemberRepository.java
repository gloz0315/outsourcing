package com.sparta.outsourcing.domain.member.repository;

import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;

public interface MemberRepository {

  void signIn(MemberSignupDto dto);

  boolean checkEmail(String email);
}
