package com.sparta.outsourcing.domain.review.model.dto;

import com.sparta.outsourcing.domain.review.model.entity.Review;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewResponseDto {

  private Long id;
  private String contents;
  private int score;
  private Long memberEntityId;
  private Long restaurantId;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public ReviewResponseDto(Long id, String contents, int score, Long memberEntityId,
      Long restaurantId, LocalDateTime createdDate, LocalDateTime updatedDate) {
    this.id = id;
    this.contents = contents;
    this.score = score;
    this.memberEntityId = memberEntityId;
    this.restaurantId = restaurantId;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

  public ReviewResponseDto(Review review) {
    this.id = review.getId();
    this.contents = review.getContents();
    this.score = review.getScore();
    this.memberEntityId = review.getMemberEntityId();
    this.restaurantId = review.getRestaurantId();
    this.createdDate = review.getCreatedDate();
    this.updatedDate = review.getUpdatedDate();
  }
}