package com.sparta.outsourcing.domain.member.repository.member;

import static com.sparta.outsourcing.global.exception.CustomError.CURRENT_PASSWORD_ERROR;
import static com.sparta.outsourcing.global.exception.CustomError.MEMBER_NOT_EXISTS;
import static com.sparta.outsourcing.global.exception.CustomError.PASSWORD_ERROR;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.MemberRole;
import com.sparta.outsourcing.domain.member.model.entity.History;
import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.history.HistoryJpaRepository;
import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdateDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdatePasswordDto;
import com.sparta.outsourcing.global.exception.CustomException;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

  private final MemberJpaRepository memberJpaRepository;
  private final HistoryJpaRepository historyJpaRepository;
  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  public void init() {
    MemberEntity member = MemberEntity.builder()
        .email("admin@naver.com")
        .nickname("관리자")
        .password(passwordEncoder.encode("Test1234"))
        .address("테스트 주소")
        .number("010-0000-0000")
        .role(MemberRole.ADMIN)
        .build();

    if (!checkEmail(member.getEmail())) {
      memberJpaRepository.save(member);
    }
  }

  @Override
  public void signIn(MemberSignupDto dto) {
    memberJpaRepository.save(MemberEntity.of(dto.getEmail(),
        dto.getNickname(), passwordEncoder.encode(dto.getPassword()),
        dto.getAddress(), dto.getNumber()));
  }

  @Override
  public void updateMember(UpdateDto dto, Long memberId) {
    MemberEntity memberEntity = memberJpaRepository.findById(memberId).orElseThrow(
        () -> new CustomException(MEMBER_NOT_EXISTS));

    if (!passwordEncoder.matches(dto.getPassword(), memberEntity.getPassword())) {
      throw new CustomException(PASSWORD_ERROR);
    }

    memberEntity.updateMember(dto);
  }

  @Override
  public void updatePasswordMember(UpdatePasswordDto dto, Long memberId) {
    MemberEntity memberEntity = memberJpaRepository.findById(memberId).orElseThrow(
        () -> new CustomException(MEMBER_NOT_EXISTS)
    );

    if (!passwordEncoder.matches(dto.getCurrentPassword(), memberEntity.getPassword())) {
      throw new CustomException(PASSWORD_ERROR);
    }

    List<History> passwordHistoryEntityList = historyJpaRepository.findTop3Member(memberId);

    passwordHistoryEntityList.forEach(
        passwordHistoryEntity -> {
          if (passwordEncoder.matches(dto.getChangePassword(),
              passwordHistoryEntity.getPassword())) {
            throw new CustomException(CURRENT_PASSWORD_ERROR);
          }
        }
    );

    memberEntity.updatePassword(passwordEncoder.encode(dto.getChangePassword()));
    History history = History.of(memberId, passwordEncoder.encode(dto.getChangePassword()));
    historyJpaRepository.save(history);
  }

  @Override
  public void deleteMember(Long memberId) {
    MemberEntity memberEntity = memberJpaRepository.findById(memberId).orElseThrow(
        () -> new CustomException(MEMBER_NOT_EXISTS));

    memberJpaRepository.delete(memberEntity);
  }

  @Override
  public boolean checkEmail(String email) {
    return memberJpaRepository.findByEmail(email).isPresent();
  }

  @Override
  public Member findMemberOrElseThrow(String email) {
    return memberJpaRepository.findByEmail(email).orElseThrow(
        () -> new CustomException(MEMBER_NOT_EXISTS)
    ).toModel();
  }

  @Override
  public Member findMemberOrElseThrow(Long memberId) {
    return memberJpaRepository.findById(memberId).orElseThrow(
        () -> new CustomException(MEMBER_NOT_EXISTS)
    ).toModel();
  }
}
