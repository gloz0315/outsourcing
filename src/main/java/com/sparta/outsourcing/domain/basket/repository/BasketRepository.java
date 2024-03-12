package com.sparta.outsourcing.domain.basket.repository;

import com.sparta.outsourcing.domain.basket.model.Basket;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BasketRepository {

  void register(Basket basket);

  void deleteBasket(Long memberId);

  List<Basket> basketInfo(Long memberId);

  Page<Basket> findAll(Long memberId, Pageable pageable);
}
