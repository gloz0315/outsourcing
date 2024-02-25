package com.sparta.outsourcing.global.jwt.repository;

public interface TokenRepository {

  void register(Long memberId, String token);
  Long findMemberIdByToken(String token);
  void deleteByMemberId(Long memberId);
  void deleteToken(String token);
}
