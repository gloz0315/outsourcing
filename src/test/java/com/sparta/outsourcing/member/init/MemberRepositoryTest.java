package com.sparta.outsourcing.member.init;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.member.MemberRepository;
import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdateDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdatePasswordDto;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemberRepositoryTest implements MemberRepository {

  private final Map<Long, MemberEntity> repository = new HashMap<>();
  private Long memberId = 0L;

  @Override
  public void signIn(MemberSignupDto dto) {

  }

  @Override
  public void updateMember(UpdateDto dto, Long memberId) {

  }

  @Override
  public void updatePasswordMember(UpdatePasswordDto dto, Long memberId) {

  }

  @Override
  public void deleteMember(Long memberId) {

  }

  @Override
  public boolean checkEmail(String email) {
    Optional<Member> member = repository.values().stream().
        filter(memberEntity -> memberEntity.getEmail().equals(email))
        .map(MemberEntity::toModel)
        .findAny();

    return member.isEmpty();
  }

  @Override
  public Member findMemberOrElseThrow(String email) {
    return null;
  }

  @Override
  public Member findMemberOrElseThrow(Long memberId) {
    return null;
  }
}
