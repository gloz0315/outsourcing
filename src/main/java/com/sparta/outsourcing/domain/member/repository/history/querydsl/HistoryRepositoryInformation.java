package com.sparta.outsourcing.domain.member.repository.history.querydsl;

import com.sparta.outsourcing.domain.member.model.entity.History;
import java.util.List;

public interface HistoryRepositoryInformation {

  // history 테이블에서 최근 변경된 비밀번호 3개를 조회 및 반환하는 메서드
  List<History> findTop3Member(Long memberId);
}
