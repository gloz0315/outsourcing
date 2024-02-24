package com.sparta.outsourcing.domain.member.service;

import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.member.service.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.controller.dto.SignupRequestDto;
import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberResponseDto signup(SignupRequestDto dto) {
    MemberSignupDto info = MemberSignupDto.builder()
        .email(dto.getEmail())
        .nickname(dto.getNickname())
        .password(dto.getPassword())
        .number(dto.getNumber())
        .address(dto.getAddress())
        .build();

    if (memberRepository.findByEmail(info.getEmail()).isPresent()) {
      throw new IllegalArgumentException("유저가 존재합니다.");
    }

    memberRepository.save(MemberEntity.of(info.getEmail(), info.getNickname(), info.getPassword(),
        info.getAddress(), info.getNumber()));

    return MemberResponseDto.builder()
        .email(info.getEmail())
        .nickname(info.getNickname())
        .build();
  }
}
