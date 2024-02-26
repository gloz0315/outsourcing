package com.sparta.outsourcing.domain.review.repository;

import com.sparta.outsourcing.domain.review.model.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByMemberEntityIdAndRestaurantId(Long memberId, Long restaurantId);

  Optional<Review> findByIdAndMemberEntityIdAndRestaurantId(Long id, Long memberEntityId,
      Long restaurantId);

}
