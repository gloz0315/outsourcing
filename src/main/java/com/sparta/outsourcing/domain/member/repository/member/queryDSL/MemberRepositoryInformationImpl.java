package com.sparta.outsourcing.domain.member.repository.member.queryDSL;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.model.entity.QMemberEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryInformationImpl implements MemberRepositoryInformation {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<MemberEntity> findByEmail(String email) {
    QMemberEntity member = QMemberEntity.memberEntity;

    return Optional.ofNullable(jpaQueryFactory.selectFrom(member)
        .where(member.email.eq(email))
        .fetchFirst());
  }
}
