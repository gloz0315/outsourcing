package com.sparta.outsourcing.domain.menu.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.domain.menu.model.entity.MenuEntity;
import com.sparta.outsourcing.domain.menu.model.entity.QMenuEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryInformationImpl implements MenuRepositoryInformation {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<MenuEntity> findByRestaurantId(Long restaurantId) {
    QMenuEntity menu = QMenuEntity.menuEntity;

    return jpaQueryFactory.selectFrom(menu)
        .where(menu.restaurantId.eq(restaurantId))
        .fetch();
  }

  @Override
  public Optional<MenuEntity> findByRestaurantIdAndId(Long restaurantId, Long id) {
    QMenuEntity menu = QMenuEntity.menuEntity;

    return Optional.ofNullable(
        jpaQueryFactory.selectFrom(menu)
            .where(menu.restaurantId.eq(restaurantId), menu.id.eq(id))
            .fetchFirst()
    );
  }

  @Override
  public Optional<MenuEntity> findByRestaurantIdAndName(Long restaurantId, String name) {
    QMenuEntity menu = QMenuEntity.menuEntity;

    return Optional.ofNullable(
        jpaQueryFactory.selectFrom(menu)
            .where(menu.restaurantId.eq(restaurantId), menu.name.eq(name))
            .fetchFirst()
    );
  }
}
