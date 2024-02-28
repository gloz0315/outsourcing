package com.sparta.outsourcing.domain.member.repository;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdateDto;

public interface MemberRepository {

  void signIn(MemberSignupDto dto);

  void updateMember(UpdateDto dto, Long memberId);

  void deleteMember(Long memberId);

  boolean checkEmail(String email);

  Member findMemberOrElseThrow(String email);

  Member findMemberOrElseThrow(Long memberId);
}
