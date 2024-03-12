package com.sparta.outsourcing.member.repository;

import com.sparta.outsourcing.domain.member.controller.dto.UpdatePasswordRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdateRequestDto;
import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.history.HistoryJpaRepository;
import com.sparta.outsourcing.domain.member.repository.member.MemberJpaRepository;
import com.sparta.outsourcing.domain.member.repository.member.MemberRepositoryImpl;
import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdateDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdatePasswordDto;
import com.sparta.outsourcing.global.config.PasswordConfig;
import com.sparta.outsourcing.global.config.QueryDSLConfig;
import com.sparta.outsourcing.global.exception.CustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import({QueryDSLConfig.class, PasswordConfig.class})
public class MemberRepositoryTest {

  @Autowired
  private MemberJpaRepository memberJpaRepository;

  @Autowired
  private HistoryJpaRepository historyJpaRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private MemberRepositoryImpl memberRepository;

  private static Long memberId = 0L;

  @BeforeEach
  void setUp() {
    memberRepository = new MemberRepositoryImpl(memberJpaRepository, historyJpaRepository,
        passwordEncoder);

    MemberEntity member = MemberEntity.of("test123@naver.com", "테스트",
        passwordEncoder.encode("Sk123456"), "테스트주소", "010-0000-0000");
    memberJpaRepository.saveAndFlush(member);
    memberId++;
  }

  @AfterEach
  void delete() {
    memberJpaRepository.deleteAll();
    historyJpaRepository.deleteAll();
  }

  @Test
  @DisplayName("회원 조회 테스트 실패_이름조회")
  void 회원조회_테스트_실패_이름조회() {
    // given
    String email = "badTest123@naver.com";

    // when, then
    Assertions.assertThrows(CustomException.class,
        () -> memberRepository.findMemberOrElseThrow(email), "해당 유저가 존재하지 않습니다.");
  }

  @Nested
  @DisplayName("회원 가입 테스트")
  class SignupTest {

    @Test
    @DisplayName("회원 가입 성공")
    void 회원가입_성공() {
      // given
      MemberSignupDto dto = MemberSignupDto.builder().email("test1234@naver.com")
          .password("Sk123456").nickname("회원가입테스트").address("회원가입테스트주소").number("010-0000-0000")
          .build();
      memberId++;

      // when
      memberRepository.signIn(dto);
      Member member = memberRepository.findMemberOrElseThrow(dto.getEmail());

      // then
      Assertions.assertEquals(dto.getEmail(), member.getEmail());
      Assertions.assertEquals(dto.getNickname(), member.getNickname());
      Assertions.assertTrue(passwordEncoder.matches(dto.getPassword(), member.getPassword()));
      Assertions.assertEquals(dto.getNumber(), member.getNumber());
      Assertions.assertEquals(dto.getAddress(), member.getAddress());
    }
  }

  @Nested
  @DisplayName("회원 수정 테스트")
  class UpdateTest {

    @Test
    @DisplayName("회원 수정 성공")
    void 회원수정_성공() {
      // given
      UpdateRequestDto updateRequestDto = UpdateRequestDto.builder().nickname("변경할 이름")
          .address("변경할 테스트 주소").number("010-1234-1234").password("Sk123456").build();

      UpdateDto dto = new UpdateDto(updateRequestDto);

      // when
      memberRepository.updateMember(dto, memberId);
      Member member = memberRepository.findMemberOrElseThrow(memberId);

      // then
      Assertions.assertEquals(dto.getNickname(), member.getNickname());
      Assertions.assertEquals(dto.getAddress(), member.getAddress());
      Assertions.assertEquals(dto.getNumber(), member.getNumber());
    }

    @Test
    @DisplayName("회원 수정 실패 틀린 비밀번호")
    void 회원수정_실패_틀린_비밀번호() {
      // given
      UpdateRequestDto updateRequestDto = UpdateRequestDto.builder().nickname("변경할 이름")
          .address("변경할 테스트 주소").number("010-1234-1234").password("Sk1234567").build();

      UpdateDto dto = new UpdateDto(updateRequestDto);

      // when, then
      Assertions.assertThrows(CustomException.class,
          () -> memberRepository.updateMember(dto, memberId));
    }

    @Test
    @DisplayName("회원 수정 실패 존재하지 않은 유저")
    void 회원수정_실패_존재하지않은_유저() {
      // given
      UpdateRequestDto updateRequestDto = UpdateRequestDto.builder().nickname("변경할 이름")
          .address("변경할 테스트 주소").number("010-1234-1234").password("Sk1234567").build();

      UpdateDto dto = new UpdateDto(updateRequestDto);

      // when, then
      Assertions.assertThrows(CustomException.class, () -> memberRepository.updateMember(dto, 0L));
    }
  }

  @Nested
  @DisplayName("비밀번호 수정 테스트")
  class UpdatePasswordTest {

    @Test
    @DisplayName("비밀번호 수정 성공")
    void 비밀번호_수정() {
      // given
      UpdatePasswordRequestDto dto = new UpdatePasswordRequestDto("Change@1234", "Sk123456",
          "Sk123456");

      UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto(dto);

      // when
      memberRepository.updatePasswordMember(updatePasswordDto, memberId);
      Member member = memberRepository.findMemberOrElseThrow(memberId);

      // then
      Assertions.assertTrue(passwordEncoder.matches(dto.getChangePassword(), member.getPassword()));
    }

    @Test
    @DisplayName("비밀번호 수정 실패 존재하지 않은 유저")
    void 비밀번호_수정_실패_존재하지않은_유저() {
      // given
      UpdatePasswordRequestDto dto = new UpdatePasswordRequestDto("Change@1234", "Sk123456",
          "Sk123456");

      UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto(dto);

      // when, then
      Assertions.assertThrows(CustomException.class,
          () -> memberRepository.updatePasswordMember(updatePasswordDto, 0L));
    }

    @Test
    @DisplayName("비밀번호 수정 불일치 비밀번호 확인")
    void 비밀번호_수정_실패_불일치_비밀번호() {
      // given
      UpdatePasswordRequestDto dto = new UpdatePasswordRequestDto("Change@1234", "Sk123456",
          "S123123");

      UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto(dto);

      // when, then
      Assertions.assertThrows(CustomException.class, updatePasswordDto::checkChangePasswordEquals);
    }
  }

  @Nested
  @DisplayName("회원 삭제 테스트")
  class DeleteTest {

    @Test
    @Order(1)
    @DisplayName("회원 삭제 성공")
    void 회원_삭제_성공() {
      // given, when
      memberRepository.deleteMember(memberId);

      // then
      Assertions.assertThrows(CustomException.class,
          () -> memberRepository.findMemberOrElseThrow(memberId));
    }

    @Test
    @Order(2)
    @DisplayName("회원 삭제 실패")
    void 회원_삭제_실패_존재하지않은_유저() {
      // when, then
      Assertions.assertThrows(CustomException.class, () -> memberRepository.deleteMember(0L));
    }
  }

  @Nested
  @DisplayName("회원 이메일 체크")
  class CheckEmail {

    @Test
    @DisplayName("회원 이메일 체크 성공")
    void 회원_이메일_체크_성공() {
      // given
      String email = "test123@naver.com";

      // when
      boolean checkEmail = memberRepository.checkEmail(email);

      // then
      Assertions.assertTrue(checkEmail);
    }

    @Test
    @DisplayName("회원 이메일 체크 실패")
    void 회원_이메일_체크_실패_잘못된_이메일() {
      // given
      String email = "wrong123@naver.com";

      // when
      boolean checkEmail = memberRepository.checkEmail(email);

      // then
      Assertions.assertFalse(checkEmail);
    }
  }
}
