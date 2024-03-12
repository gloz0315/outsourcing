package com.sparta.outsourcing.domain.basket.repository.querydsl;

import static com.sparta.outsourcing.domain.basket.model.entity.QBasketEntity.basketEntity;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
import com.sparta.outsourcing.domain.basket.model.entity.QBasketEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BasketRepositoryInformationImpl implements BasketRepositoryInformation {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<BasketEntity> findBasketEntityByMemberId(Long memberId) {
    QBasketEntity basket = basketEntity;

    return jpaQueryFactory.selectFrom(basket)
        .where(basket.memberId.eq(memberId))
        .fetch();
  }

  @Override
  public BasketEntity findFirstByMemberIdAndMenuId(Long memberId, Long menuId) {
    QBasketEntity basket = basketEntity;

    return jpaQueryFactory.selectFrom(basket)
        .where(basket.memberId.eq(memberId), basket.menuId.eq(menuId))
        .fetchOne();
  }

  @Override
  public Page<Basket> findAll(Long memberId, Pageable pageable) {
    JPAQuery<BasketEntity> query = jpaQueryFactory.select(basketEntity)
        .from(basketEntity)
        .where(basketEntity.memberId.eq(memberId))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Basket> baskets = query.fetch().stream().map(BasketEntity::toModel).toList();

    long totalSize = jpaQueryFactory.select(Wildcard.count)
        .from(basketEntity)
        .fetch().get(0);

    return PageableExecutionUtils.getPage(baskets, pageable, () -> totalSize);
  }
}
