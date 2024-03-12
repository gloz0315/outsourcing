package com.sparta.outsourcing.domain.basket.repository.querydsl;

import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BasketRepositoryInformation {

  List<BasketEntity> findBasketEntityByMemberId(Long memberId);

  BasketEntity findFirstByMemberIdAndMenuId(Long memberId, Long menuId);

  Page<Basket> findAll(Long memberId, Pageable pageable);
}
