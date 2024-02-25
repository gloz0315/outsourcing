package com.sparta.outsourcing.domain.member.controller;

import com.sparta.outsourcing.domain.member.service.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.controller.dto.SignupRequestDto;
import com.sparta.outsourcing.domain.member.service.MemberService;
import com.sparta.outsourcing.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<CommonResponseDto<MemberResponseDto>> signup(
      @Validated @RequestBody SignupRequestDto dto
  ) {
    MemberResponseDto responseDto = memberService.signup(dto);
    return CommonResponseDto.ok("회원가입에 성공하셨습니다.", responseDto);
  }
}
