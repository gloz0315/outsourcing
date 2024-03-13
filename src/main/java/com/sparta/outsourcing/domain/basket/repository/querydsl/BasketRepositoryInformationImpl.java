package com.sparta.outsourcing.domain.basket.repository.querydsl;

import static com.sparta.outsourcing.domain.basket.model.entity.QBasketEntity.basketEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
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

    return jpaQueryFactory.select(basketEntity)
        .from(basketEntity)
        .where(memberIdEq(memberId))
        .fetch();
  }

  @Override
  public BasketEntity findFirstByMemberIdAndMenuId(Long memberId, Long menuId) {

    return jpaQueryFactory.select(basketEntity)
        .from(basketEntity)
        .where(menuIdEq(menuId), memberIdEq(memberId))
        .fetchOne();
  }

  @Override
  public Page<Basket> findAll(Long memberId, Pageable pageable) {
    JPAQuery<BasketEntity> query = jpaQueryFactory.select(basketEntity)
        .from(basketEntity)
        .where(memberIdEq(memberId))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Basket> baskets = query.fetch().stream().map(BasketEntity::toModel).toList();

    JPAQuery<Long> count = jpaQueryFactory.select(basketEntity.count())
        .from(basketEntity)
        .where(memberIdEq(memberId));

    return PageableExecutionUtils.getPage(baskets, pageable, count::fetchOne);
  }

  private BooleanExpression memberIdEq(Long memberId) {
    return memberId != null ? basketEntity.memberId.eq(memberId) : null;
  }

  private BooleanExpression menuIdEq(Long menuId) {
    return menuId != null ? basketEntity.menuId.eq(menuId) : null;
  }
}
