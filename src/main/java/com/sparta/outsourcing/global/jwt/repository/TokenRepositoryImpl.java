package com.sparta.outsourcing.global.jwt.repository;

import static com.sparta.outsourcing.global.exception.JwtError.INVALID_TOKEN;

import com.sparta.outsourcing.global.exception.CustomJwtException;
import com.sparta.outsourcing.global.jwt.entity.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository{
  private final RefreshTokenJpaRepository refreshTokenJpaRepository;

  @Override
  public void register(Long memberId, String token) {
    RefreshTokenEntity entity = RefreshTokenEntity.of(memberId, token);
    refreshTokenJpaRepository.save(entity);
  }

  @Override
  public Long findMemberIdByToken(String token) {
    RefreshTokenEntity entity = refreshTokenJpaRepository.findByToken(token)
        .orElseThrow(
            () -> new CustomJwtException(INVALID_TOKEN));
    return entity.getMemberId();
  }

  @Override
  public void deleteByMemberId(Long memberId) {
    refreshTokenJpaRepository.deleteByMemberId(memberId);
  }

  @Override
  public void deleteToken(String token) {
    refreshTokenJpaRepository.deleteByToken(token);
  }
}
