package com.sparta.outsourcing.domain.member.repository;

import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

  Optional<MemberEntity> findByEmail(String email);
}
