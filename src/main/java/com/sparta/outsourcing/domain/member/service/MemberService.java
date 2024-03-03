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

  MemberInfoResponse memberInfo(Long memberId);

  void updateMember(Long memberId, String email, UpdateRequestDto dto);

  void deleteMember(Long memberId, String username);

  void updatePasswordMember(Long memberId, String email, UpdatePasswordRequestDto dto);
}
