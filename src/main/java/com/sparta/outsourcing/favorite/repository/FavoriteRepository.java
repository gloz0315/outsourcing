package com.sparta.outsourcing.favorite.repository;

import com.sparta.outsourcing.favorite.model.entity.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  Optional<Favorite> findByMemberIdAndRestaurantId(Long memberId, Long restaurantId);

  List<Favorite> findAllByMemberId(Long memberId);

  List<Favorite> findByRestaurantIdAndMemberId(Long restaurantId, Long memberId);

}

