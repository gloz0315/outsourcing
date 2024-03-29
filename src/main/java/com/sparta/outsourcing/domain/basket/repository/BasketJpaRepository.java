package com.sparta.outsourcing.domain.basket.repository;

import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
import com.sparta.outsourcing.domain.basket.repository.querydsl.BasketRepositoryInformation;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketJpaRepository extends JpaRepository<BasketEntity, Long>,
    BasketRepositoryInformation {

  List<BasketEntity> findAllByMemberId(Long memberId, Pageable pageable);
}
