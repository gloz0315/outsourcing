package com.sparta.outsourcing.domain.member.controller;

import static com.sparta.outsourcing.global.success.SuccessCode.*;

import com.sparta.outsourcing.domain.member.controller.dto.SignupRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdatePasswordRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdateRequestDto;
import com.sparta.outsourcing.domain.member.service.MemberService;
import com.sparta.outsourcing.domain.member.service.dto.MemberInfoResponse;
import com.sparta.outsourcing.domain.member.service.dto.MemberResponseDto;
import com.sparta.outsourcing.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    return CommonResponseDto.ok(SUCCESS_SIGNUP, responseDto);
  }

  @PostMapping("/logout")
  public ResponseEntity<CommonResponseDto<String>> logout(
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    memberService.logout(userDetails);
    return CommonResponseDto.ok(SUCCESS_LOGOUT, null);
  }

  @GetMapping("/{memberId}")
  public ResponseEntity<CommonResponseDto<MemberInfoResponse>> memberInfo(
      @PathVariable("memberId") Long memberId
  ) {
    MemberInfoResponse response = memberService.memberInfo(memberId);
    return CommonResponseDto.ok(SUCCESS_SEARCH, response);
  }

  @PutMapping("/{memberId}")
  public ResponseEntity<CommonResponseDto<Void>> updateMember(
      @PathVariable("memberId") Long memberId,
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody UpdateRequestDto dto
  ) {
    memberService.updateMember(memberId, userDetails.getUsername(), dto);
    return CommonResponseDto.ok(SUCCESS_UPDATE_MEMBER, null);
  }

  @PatchMapping("/{memberId}")
  public ResponseEntity<CommonResponseDto<Void>> updatePasswordMember(
      @PathVariable("memberId") Long memberId,
      @AuthenticationPrincipal UserDetails userDetails,
      @Validated @RequestBody UpdatePasswordRequestDto dto
  ) {
    memberService.updatePasswordMember(memberId, userDetails.getUsername(), dto);
    return CommonResponseDto.ok(SUCCESS_UPDATE_MEMBER_PASSWORD, null);
  }

  @DeleteMapping("/{memberId}")
  public ResponseEntity<CommonResponseDto<Void>> deleteMember(
      @PathVariable("memberId") Long memberId,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    memberService.deleteMember(memberId, userDetails.getUsername());
    return CommonResponseDto.ok(SUCCESS_DELETE_MEMBER, null);
  }
}
