package com.sparta.outsourcing.global.jwt.repository;

import com.sparta.outsourcing.global.jwt.entity.RefreshTokenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {

  Optional<RefreshTokenEntity> findByMemberId(Long id);

  void deleteByToken(String token);
}
