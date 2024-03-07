package com.sparta.outsourcing.domain.basket.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
import com.sparta.outsourcing.domain.basket.model.entity.QBasketEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BasketRepositoryInformationImpl implements BasketRepositoryInformation {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<BasketEntity> findBasketEntityByMemberId(Long memberId) {
    QBasketEntity basket = QBasketEntity.basketEntity;

    return jpaQueryFactory.selectFrom(basket)
        .where(basket.memberId.eq(memberId))
        .fetch();
  }

  @Override
  public BasketEntity findFirstByMemberIdAndMenuId(Long memberId, Long menuId) {
    QBasketEntity basket = QBasketEntity.basketEntity;

    return jpaQueryFactory.selectFrom(basket)
        .where(basket.memberId.eq(memberId), basket.menuId.eq(menuId))
        .fetchOne();
  }
}
