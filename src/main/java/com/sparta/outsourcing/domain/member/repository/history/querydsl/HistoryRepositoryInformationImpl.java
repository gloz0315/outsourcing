package com.sparta.outsourcing.domain.member.repository.history.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.domain.member.model.entity.History;
import com.sparta.outsourcing.domain.member.model.entity.QHistory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HistoryRepositoryInformationImpl implements HistoryRepositoryInformation {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<History> findTop3Member(Long memberId) {
    QHistory history = QHistory.history;

    return jpaQueryFactory.selectFrom(history)
        .where(history.memberId.eq(memberId))
        .orderBy(history.createdDate.desc())
        .limit(3)
        .fetch();
  }
}
