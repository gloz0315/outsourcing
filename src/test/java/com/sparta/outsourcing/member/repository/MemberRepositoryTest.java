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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class MemberRepositoryTest {

  @Autowired
  private MemberJpaRepository memberJpaRepository;

  @Autowired
  private HistoryJpaRepository historyJpaRepository;

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  private MemberRepositoryImpl memberRepository;
  private static Long memberId = 0L;
  private static Long historyId = 0L;

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
  @DisplayName("회원 가입 성공")
  void 회원가입() {
    // given
    MemberSignupDto dto = MemberSignupDto.builder()
        .email("test1234@naver.com")
        .password("Sk123456")
        .nickname("회원가입테스트")
        .address("회원가입테스트주소")
        .number("010-0000-0000")
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

  @Test
  @DisplayName("회원 수정")
  void 회원수정() {
    // given
    UpdateRequestDto updateRequestDto = UpdateRequestDto.builder()
        .nickname("변경할 이름")
        .address("변경할 테스트 주소")
        .number("010-1234-1234")
        .password("Sk123456")
        .build();

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
  @DisplayName("비밀번호 수정")
  void 비밀번호_수정() {
    // given
    UpdatePasswordRequestDto dto = new UpdatePasswordRequestDto(
        "Change@1234", "Sk123456", "Sk123456"
    );

    UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto(dto);

    // when
    memberRepository.updatePasswordMember(updatePasswordDto, memberId);
    Member member = memberRepository.findMemberOrElseThrow(memberId);

    // then
    Assertions.assertTrue(passwordEncoder.matches(dto.getChangePassword(), member.getPassword()));
  }
}
