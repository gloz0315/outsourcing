package com.sparta.outsourcing.global.jwt.repository;

import static com.sparta.outsourcing.global.exception.CustomError.NOT_EXIST_TOKEN;

import com.sparta.outsourcing.global.exception.CustomException;
import com.sparta.outsourcing.global.jwt.entity.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

  private final RefreshTokenJpaRepository refreshTokenJpaRepository;

  @Override
  public void register(Long memberId, String token) {
    RefreshTokenEntity entity = RefreshTokenEntity.of(memberId, token);
    refreshTokenJpaRepository.save(entity);
  }

  @Override
  public RefreshTokenEntity findByMemberId(Long memberId) {
    return refreshTokenJpaRepository.findByMemberId(memberId).orElseThrow(
        () -> new CustomException(NOT_EXIST_TOKEN)
    );
  }

  @Override
  public void deleteToken(RefreshTokenEntity token) {
    refreshTokenJpaRepository.deleteByToken(token.getToken());
  }
}
