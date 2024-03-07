package com.sparta.outsourcing.domain.member.repository.member.querydsl;

import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import java.util.Optional;

public interface MemberRepositoryInformation {

  Optional<MemberEntity> findByEmail(String email);
}
