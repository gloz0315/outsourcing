package com.sparta.outsourcing.domain.basket.repository;

import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BasketRepositoryImpl implements BasketRepository {

  private final BasketJpaRepository basketJpaRepository;

  @Override
  public void register(Basket basket) {
    BasketEntity basketInfo = basketJpaRepository.findFirstByMemberIdAndMenuId(
        basket.getMemberId(), basket.getMenuId());

    if (basketInfo == null) {
      basketJpaRepository.save(
          BasketEntity.of(basket.getMemberId(), basket.getRestaurantId(), basket.getMenuId(),
              basket.getCount()));
      return;
    }

    basketInfo.update(basketInfo.getCount());
  }

  @Override
  public void deleteBasket(Long memberId) {
    List<BasketEntity> basketInfo = basketJpaRepository.findBasketEntityByMemberId(memberId);

    basketJpaRepository.deleteAll(basketInfo);
  }

  @Override
  public List<Basket> basketInfo(Long memberId) {
    List<BasketEntity> basketInfo = basketJpaRepository.findBasketEntityByMemberId(memberId);

    return basketInfo.stream().map(BasketEntity::toModel).toList();
  }

  @Override
  public Page<Basket> findAll(Long memberId, Pageable pageable) {

    return basketJpaRepository.findAll(memberId, pageable);
  }

  @Override
  public List<Basket> findAllJpa(Long memberId, Pageable pageable) {

    return basketJpaRepository.findAllByMemberId(memberId, pageable).stream()
        .map(BasketEntity::toModel)
        .toList();
  }
}
