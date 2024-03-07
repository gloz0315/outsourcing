package com.sparta.outsourcing.domain.basket.repository.querydsl;

import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
import java.util.List;

public interface BasketRepositoryInformation {

  List<BasketEntity> findBasketEntityByMemberId(Long memberId);

  BasketEntity findFirstByMemberIdAndMenuId(Long memberId, Long menuId);
}
