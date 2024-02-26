package com.sparta.outsourcing.domain.review.controller;

import com.sparta.outsourcing.domain.restaurant.dto.RestaurantsRequestDto;
import com.sparta.outsourcing.domain.restaurant.dto.RestaurantsResponseDto;
import com.sparta.outsourcing.domain.restaurant.entity.CommonResponse;
import com.sparta.outsourcing.domain.review.model.dto.ReviewRequestDto;
import com.sparta.outsourcing.domain.review.model.dto.ReviewResponseDto;
import com.sparta.outsourcing.domain.review.model.entity.Review;
import com.sparta.outsourcing.domain.review.service.ReviewService;
import jakarta.persistence.Id;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

  private final ReviewService reviewService;


  @PostMapping
  public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto requestDto) {
    ReviewResponseDto reviewResponseDto = reviewService.createReview(requestDto);
    return ResponseEntity.ok(reviewResponseDto);
  }

  @GetMapping
  public ResponseEntity<List<ReviewResponseDto>> findReviews(
      @RequestParam Long memberEntityId,
      @RequestParam Long restaurantId) {
    List<ReviewResponseDto> reviews = reviewService.findReviews(memberEntityId, restaurantId);
    return ResponseEntity.ok(reviews);
  }


  @GetMapping("/{id}")
  public ResponseEntity<ReviewResponseDto> findReviewById(@PathVariable Long id) {
    ReviewResponseDto reviewResponseDto = reviewService.findReviewById(id);
    return ResponseEntity.ok(reviewResponseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReviewResponseDto> updateReview(
      @PathVariable Long id,
      @RequestParam Long memberEntityId,
      @RequestParam Long restaurantId,
      @RequestBody ReviewRequestDto requestDto) {

    ReviewResponseDto updatedReview = reviewService.updateReview(id, memberEntityId, restaurantId,
        requestDto);
    return ResponseEntity.ok(updatedReview);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReview(
      @PathVariable Long id,
      @RequestParam Long memberEntityId,
      @RequestParam Long restaurantId) {

    reviewService.deleteReview(id, memberEntityId, restaurantId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}