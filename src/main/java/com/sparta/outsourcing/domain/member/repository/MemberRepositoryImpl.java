package com.sparta.outsourcing.domain.member.repository;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

  private final MemberJpaRepository memberJpaRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void signIn(MemberSignupDto dto) {
    memberJpaRepository.save(MemberEntity.of(dto.getEmail(),
        dto.getNickname(), passwordEncoder.encode(dto.getPassword()),
        dto.getAddress(), dto.getNumber()));
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
}
