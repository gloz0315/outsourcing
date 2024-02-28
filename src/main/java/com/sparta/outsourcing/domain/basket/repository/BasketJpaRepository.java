package com.sparta.outsourcing.domain.basket.repository;

import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BasketJpaRepository extends JpaRepository<BasketEntity, Long> {

  List<BasketEntity> findBasketEntityByMemberId(Long memberId);

  @Query(value = "select b from BasketEntity b where b.memberId = ?1 and b.menuId = ?1 limit 1")
  BasketEntity findFirstByMemberIdAndAndMenuId(Long memberId, Long menuId);
}
