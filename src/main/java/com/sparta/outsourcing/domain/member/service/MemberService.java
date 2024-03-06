package com.sparta.outsourcing.domain.member.service;

import com.sparta.outsourcing.domain.member.controller.dto.SignupRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdatePasswordRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdateRequestDto;
import com.sparta.outsourcing.domain.member.service.dto.MemberInfoResponse;
import com.sparta.outsourcing.domain.member.service.dto.MemberResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface MemberService {

  MemberResponseDto signup(SignupRequestDto dto);

  void logout(UserDetails userDetails);

  // 유저의 정보를 조회
  MemberInfoResponse memberInfo(Long memberId);

  // 유저의 정보를 수정 -> (비밀번호는 제외)
  void updateMember(Long memberId, String email, UpdateRequestDto dto);

  // 유저의 정보를 수정 -> 비밀번호 수정
  void updatePasswordMember(Long memberId, String email, UpdatePasswordRequestDto dto);

  void deleteMember(Long memberId, String username);
}
