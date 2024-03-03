package com.sparta.outsourcing.member.service;

import static com.sparta.outsourcing.member.test.MemberInfo.TEST_ADDRESS;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_EMAIL;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_NAME;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_NUMBER;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.sparta.outsourcing.domain.favorite.model.entity.Favorite;
import com.sparta.outsourcing.domain.favorite.repository.FavoriteRepository;
import com.sparta.outsourcing.domain.member.controller.dto.SignupRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdateRequestDto;
import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.MemberRole;
import com.sparta.outsourcing.domain.member.repository.member.MemberRepository;
import com.sparta.outsourcing.domain.member.service.MemberService;
import com.sparta.outsourcing.domain.member.service.dto.MemberInfoResponse;
import com.sparta.outsourcing.domain.member.service.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdateDto;
import com.sparta.outsourcing.domain.review.model.entity.Review;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.global.exception.CustomException;
import com.sparta.outsourcing.global.jwt.entity.RefreshTokenEntity;
import com.sparta.outsourcing.global.jwt.repository.TokenRepository;
import com.sparta.outsourcing.global.security.UserDetailsImpl;
import com.sparta.outsourcing.member.memberInit.MemberInit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private TokenRepository tokenRepository;

  @Mock
  private ReviewRepository reviewRepository;

  @Mock
  private FavoriteRepository favoriteRepository;

  @InjectMocks
  private MemberService memberService;

  private final MemberInit memberInit = new MemberInit();

  @Nested
  @DisplayName("회원 성공 테스트")
  class SignupTest {

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void 회원_가입_성공() {
      // given
      SignupRequestDto dto = new SignupRequestDto();
      ReflectionTestUtils.setField(dto, "email", TEST_EMAIL);
      ReflectionTestUtils.setField(dto, "nickname", TEST_NAME);
      ReflectionTestUtils.setField(dto, "password", TEST_PASSWORD);
      ReflectionTestUtils.setField(dto, "address", TEST_ADDRESS);
      ReflectionTestUtils.setField(dto, "number", TEST_NUMBER);

      given(memberRepository.checkEmail(dto.getEmail())).willReturn(false);

      // when
      MemberResponseDto result = memberService.signup(dto);

      // then
      Assertions.assertEquals(result.getEmail(), dto.getEmail());
      Assertions.assertEquals(result.getNickname(), dto.getNickname());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트")
    void 회원_가입_실패_존재하는_이메일() {
      // given
      SignupRequestDto dto = new SignupRequestDto();
      ReflectionTestUtils.setField(dto, "email", TEST_EMAIL);
      ReflectionTestUtils.setField(dto, "nickname", TEST_NAME);
      ReflectionTestUtils.setField(dto, "password", TEST_PASSWORD);
      ReflectionTestUtils.setField(dto, "address", TEST_ADDRESS);
      ReflectionTestUtils.setField(dto, "number", TEST_NUMBER);

      given(memberRepository.checkEmail(dto.getEmail())).willReturn(true);

      // when, then
      Assertions.assertThrows(CustomException.class,
          () -> memberService.signup(dto));
    }
  }

  @Nested
  @DisplayName("로그아웃 테스트")
  class LogoutTest {

    @Test
    @DisplayName("로그아웃 성공")
    void 로그아웃_성공() {
      // given
      Member member = Member.builder()
          .id(1L)
          .email(TEST_EMAIL)
          .password(TEST_PASSWORD)
          .nickname(TEST_NAME)
          .address(TEST_ADDRESS)
          .number(TEST_NUMBER)
          .role(MemberRole.USER)
          .build();

      UserDetailsImpl userDetails = new UserDetailsImpl(member);
      RefreshTokenEntity refreshToken = RefreshTokenEntity.of(member.getId(), "testToken");

      given(memberRepository.findMemberOrElseThrow(userDetails.getUsername())).willReturn(member);
      given(tokenRepository.findByMemberId(member.getId())).willReturn(refreshToken);

      // when
      memberService.logout(userDetails);

      // then
      verify(memberRepository, atLeastOnce()).findMemberOrElseThrow(userDetails.getUsername());
      verify(tokenRepository, atLeastOnce()).findByMemberId(member.getId());
      verify(tokenRepository, atLeastOnce()).deleteToken(eq(refreshToken));
    }

    @Test
    @DisplayName("로그아웃 실패 존재하지 않은 유저")
    void 로그아웃_실패_존재하지않은_유저() {
      // given
      Member member = Member.builder()
          .id(1L)
          .email(TEST_EMAIL)
          .password(TEST_PASSWORD)
          .nickname(TEST_NAME)
          .address(TEST_ADDRESS)
          .number(TEST_NUMBER)
          .role(MemberRole.USER)
          .build();

      UserDetailsImpl userDetails = new UserDetailsImpl(member);
      given(memberRepository.findMemberOrElseThrow(userDetails.getUsername())).willThrow(
          CustomException.class);

      // when, then
      Assertions.assertThrows(CustomException.class,
          () -> memberService.logout(userDetails));
    }

    @Test
    @DisplayName("로그아웃 실패 존재하지 않은 토큰")
    void 로그아웃_실패_존재하지않은_토큰() {
      // given
      Member member = Member.builder()
          .id(1L)
          .email(TEST_EMAIL)
          .password(TEST_PASSWORD)
          .nickname(TEST_NAME)
          .address(TEST_ADDRESS)
          .number(TEST_NUMBER)
          .role(MemberRole.USER)
          .build();

      UserDetailsImpl userDetails = new UserDetailsImpl(member);
      given(memberRepository.findMemberOrElseThrow(userDetails.getUsername())).willReturn(member);
      given(tokenRepository.findByMemberId(member.getId())).willThrow(CustomException.class);

      // when, then
      Assertions.assertThrows(CustomException.class,
          () -> memberService.logout(userDetails));
    }
  }

  @Nested
  @DisplayName("회원 정보 조회")
  class MemberInfoTest {

    @Test
    @DisplayName("회원 정보 조회 성공")
    void 회원정보_조회_성공() {
      // given
      Member member = memberInit.init();
      List<Favorite> favoriteList = List.of(new Favorite(1L, member.getId(), 1L, LocalDate.now()));
      List<Review> reviewList = List.of(
          new Review(1L, "테스트내용", 3, LocalDateTime.now(), LocalDateTime.now(), null, member.getId(),
              1L, 1L));

      given(memberRepository.findMemberOrElseThrow(member.getId())).willReturn(member);
      given(favoriteRepository.findAllByMemberId(member.getId())).willReturn(favoriteList);
      given(reviewRepository.findByMemberEntityId(member.getId())).willReturn(reviewList);

      // when
      MemberInfoResponse result = memberService.memberInfo(member.getId());

      // then
      Assertions.assertEquals(result.getMemberId(), member.getId());
      Assertions.assertEquals(result.getFavoriteList().get(0).getCreatedDate(),
          favoriteList.get(0).getCreatedDate());
      Assertions.assertEquals(result.getReviewList().get(0).getContents(),
          reviewList.get(0).getContents());
    }

    @Test
    @DisplayName("회원 정보 조회 실패 존재하지 않은 유저")
    void 회원_정보_조회_실패_존재하지않은_유저() {
      // given
      given(memberRepository.findMemberOrElseThrow(1L)).willThrow(CustomException.class);

      // when, then
      Assertions.assertThrows(CustomException.class,
          () -> memberService.memberInfo(1L));
    }
  }

  @Nested
  @DisplayName("회원 정보 수정 테스트")
  class UpdateMemberTest {

    @Test
    @DisplayName("회원 정보 수정 성공")
    void 회원_정보_수정_성공() {
      // given
      Member member = memberInit.init();
      UpdateRequestDto dto = UpdateRequestDto.builder()
          .nickname("변경할이름")
          .address("변경할주소")
          .number("010-1234-1234")
          .password(member.getPassword())
          .build();

      given(memberRepository.findMemberOrElseThrow(member.getEmail())).willReturn(member);

      // when
      memberService.updateMember(member.getId(), member.getEmail(), dto);

      // then
      verify(memberRepository, atLeastOnce()).findMemberOrElseThrow(member.getEmail());
      verify(memberRepository, atLeastOnce()).updateMember(any(UpdateDto.class),
          eq(member.getId()));
    }
  }
}
