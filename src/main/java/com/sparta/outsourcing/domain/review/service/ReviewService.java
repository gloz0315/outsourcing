package com.sparta.outsourcing.domain.review.service;

import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.MemberJpaRepository;
import com.sparta.outsourcing.domain.restaurant.entity.Restaurants;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import com.sparta.outsourcing.domain.review.model.dto.ReviewRequestDto;
import com.sparta.outsourcing.domain.review.model.dto.ReviewResponseDto;
import com.sparta.outsourcing.domain.review.model.entity.Review;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final MemberJpaRepository memberJpaRepository;
  private final RestaurantsRepository restaurantsRepository;


  @Transactional
  public ReviewResponseDto createReview(ReviewRequestDto requestDto) {
    // 유저 확인
    MemberEntity member = memberJpaRepository.findById(requestDto.getMemberEntityId())
        .orElseThrow(() -> new IllegalArgumentException("잘못된 유저 ID"));

    // 가게 확인
    Restaurants restaurant = restaurantsRepository.findById(requestDto.getRestaurantId())
        .orElseThrow(() -> new IllegalArgumentException("잘못된 가게 ID"));

    // 리뷰 생성
    Review review = Review.builder()
        .contents(requestDto.getContents())
        .score(requestDto.getScore())
        .memberEntityId(requestDto.getMemberEntityId())
        .restaurantId(requestDto.getRestaurantId())
        .createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now())
        .build();

    review = reviewRepository.save(review);

    // 응답 생성
    return new ReviewResponseDto(review.getId(), review.getContents(), review.getScore(),
        review.getMemberEntityId(), review.getRestaurantId(), review.getCreatedDate(),
        review.getUpdatedDate());
  }

  public List<ReviewResponseDto> findReviews(Long memberEntityId, Long restaurantId) {
    List<Review> reviews;
    if (memberEntityId != null && restaurantId != null) {
      reviews = reviewRepository.findByMemberEntityIdAndRestaurantId(memberEntityId, restaurantId);
    } else {
      reviews = reviewRepository.findAll();
    }
    return reviews.stream()
        .map(
            review -> new ReviewResponseDto(review.getId(), review.getContents(), review.getScore(),
                review.getMemberEntityId(), review.getRestaurantId(), review.getCreatedDate(),
                review.getUpdatedDate()))
        .collect(Collectors.toList());
  }

  public ReviewResponseDto findReviewById(Long reviewId) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new NoSuchElementException("리뷰 id를 찾을 수 없습니다. id: " + reviewId));
    return new ReviewResponseDto(review);
  }

  @Transactional
  public ReviewResponseDto updateReview(Long reviewId, Long memberEntityId, Long restaurantId,
      ReviewRequestDto requestDto) {
    Review review = reviewRepository.findByIdAndMemberEntityIdAndRestaurantId(reviewId,
            memberEntityId, restaurantId)
        .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없거나 수정할 권한이 없습니다."));

    // 리뷰 수정
    review.setContents(requestDto.getContents());
    review.setScore(requestDto.getScore());
    review.setUpdatedDate(LocalDateTime.now());
    // 응답 생성

    reviewRepository.save(review);
    return new ReviewResponseDto(review);
  }


  @Transactional
  public void deleteReview(Long id, Long memberEntityId, Long restaurantId) {
    Review review = reviewRepository.findByIdAndMemberEntityIdAndRestaurantId(id, memberEntityId,
            restaurantId)
        .orElseThrow(() -> new IllegalArgumentException("삭제할 리뷰의 Id를 찾을 수 없습니다."));

    review.setDeletedDate(LocalDateTime.now());
    reviewRepository.save(review);
  }

}
