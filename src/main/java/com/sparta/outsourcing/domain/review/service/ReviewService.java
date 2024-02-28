package com.sparta.outsourcing.domain.review.service;

import static com.sparta.outsourcing.global.exception.CustomError.NOT_EXIST_MENU;
import static com.sparta.outsourcing.global.exception.CustomError.NOT_EXIST_REVIEW;
import static com.sparta.outsourcing.global.exception.CustomError.NO_AUTH;
import static com.sparta.outsourcing.global.exception.CustomError.RESTAURANT_NOT_EXIST;

import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.member.MemberJpaRepository;
import com.sparta.outsourcing.domain.menu.model.entity.MenuEntity;
import com.sparta.outsourcing.domain.menu.repository.MenuJpaRepository;
import com.sparta.outsourcing.domain.restaurant.entity.Restaurants;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import com.sparta.outsourcing.domain.review.model.dto.ReviewRequestDto;
import com.sparta.outsourcing.domain.review.model.dto.ReviewResponseDto;
import com.sparta.outsourcing.domain.review.model.entity.Review;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.global.exception.CustomException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final MemberJpaRepository memberJpaRepository;
  private final RestaurantsRepository restaurantsRepository;
  private final MenuJpaRepository menuJpaRepository;


  @Transactional
  public ReviewResponseDto createReview(ReviewRequestDto requestDto, UserDetails userDetails) {
    // 유저 확인
    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(NO_AUTH));
    // 가게 확인
    Restaurants restaurant = restaurantsRepository.findById(requestDto.getRestaurantId())
        .orElseThrow(() -> new CustomException(RESTAURANT_NOT_EXIST));
    // 메뉴 확인
    MenuEntity menu = menuJpaRepository.findById(requestDto.getMenuId())
        .orElseThrow(() -> new CustomException(NOT_EXIST_MENU));

    // 리뷰 생성
    Review review = Review.builder()
        .contents(requestDto.getContents())
        .score(requestDto.getScore())
        .memberEntityId(member.getId()) // UserDetails에서 얻은 사용자 ID 사용
        .restaurantId(requestDto.getRestaurantId())
        .menuId(requestDto.getMenuId())
        .createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now())
        .build();

    review = reviewRepository.save(review);

    // 응답 생성
    return new ReviewResponseDto(review.getId(), review.getContents(), review.getScore(),
        review.getMemberEntityId(), review.getRestaurantId(), review.getMenuId(),
        review.getCreatedDate(),
        review.getUpdatedDate());
  }

  public List<ReviewResponseDto> findReviews(UserDetails userDetails, Long restaurantId) {
    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("잘못된 유저 이름 입니다."));

    List<Review> reviews;
    if (restaurantId != null) {
      reviews = reviewRepository.findByMemberEntityIdAndRestaurantId(member.getId(), restaurantId);
    } else {
      reviews = reviewRepository.findByMemberEntityId(member.getId());
    }
    return reviews.stream()
        .map(ReviewResponseDto::new)
        .collect(Collectors.toList());
  }

  public ReviewResponseDto findReviewById(Long reviewId, UserDetails userDetails) {
    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(NO_AUTH));

    Review review = reviewRepository.findByIdAndMemberEntityId(reviewId, member.getId())
        .orElseThrow(() -> new CustomException(NOT_EXIST_REVIEW));

    return new ReviewResponseDto(review);
  }

  public List<ReviewResponseDto> findAllReviewsByUser(UserDetails userDetails) {
    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(NO_AUTH));

    // 유저가 작성한 모든 리뷰 조회
    List<Review> reviews = reviewRepository.findByMemberEntityId(member.getId());

    return reviews.stream()
        .map(ReviewResponseDto::new)
        .collect(Collectors.toList());
  }

  @Transactional
  public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto,
      UserDetails userDetails) {
    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(NO_AUTH));

    Review review = reviewRepository.findByIdAndMemberEntityId(reviewId, member.getId())
        .orElseThrow(() -> new CustomException(NOT_EXIST_REVIEW));

    review.setContents(requestDto.getContents());
    review.setScore(requestDto.getScore());
    review.setUpdatedDate(LocalDateTime.now());

    reviewRepository.save(review); // 한번 테스트 해봐야겠다..
    return new ReviewResponseDto(review);
  }

  @Transactional
  public void deleteReview(Long reviewId, UserDetails userDetails) {
    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(NO_AUTH));

    Review review = reviewRepository.findByIdAndMemberEntityId(reviewId, member.getId())
        .orElseThrow(() -> new CustomException(NOT_EXIST_REVIEW));

    review.setDeletedDate(LocalDateTime.now());
    reviewRepository.save(review); // 요것도..
  }


}
