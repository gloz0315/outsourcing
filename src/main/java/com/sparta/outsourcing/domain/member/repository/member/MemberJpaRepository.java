package com.sparta.outsourcing.domain.member.repository.member;

import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.member.queryDSL.MemberRepositoryInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long>,
    MemberRepositoryInformation {

  // Optional<MemberEntity> findByEmail(String email);
}
