package com.sparta.outsourcing.domain.member.repository;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.MemberRole;
import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdateDto;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

  private final MemberJpaRepository memberJpaRepository;
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
        () -> new EntityNotFoundException("해당 유저의 정보가 존재하지 않습니다.")
    );

    memberEntity.updateMember(dto);

    if (!dto.getPassword().isEmpty()) {
      String changePassword = passwordEncoder.encode(dto.getPassword());
      memberEntity.updatePassword(changePassword);
    }
  }

  @Override
  public void deleteMember(Long memberId) {
    MemberEntity memberEntity = memberJpaRepository.findById(memberId).orElseThrow(
        () -> new EntityNotFoundException("해당 유저의 정보가 존재하지 않습니다.")
    );

    memberJpaRepository.delete(memberEntity);
  }

  @Override
  public boolean checkEmail(String email) {
    return memberJpaRepository.findByEmail(email).isPresent();
  }

  @Override
  public Member findMemberOrElseThrow(String email) {
    return memberJpaRepository.findByEmail(email).orElseThrow(
        () -> new EntityNotFoundException("해당 유저가 존재하지 않습니다.")
    ).toModel();
  }

  @Override
  public Member findMemberOrElseThrow(Long memberId) {
    return memberJpaRepository.findById(memberId).orElseThrow(
        () -> new EntityNotFoundException("해당 유저가 존재하지 않습니다.")
    ).toModel();
  }
}
