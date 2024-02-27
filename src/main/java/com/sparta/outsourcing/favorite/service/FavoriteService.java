package com.sparta.outsourcing.favorite.service;

import com.sparta.outsourcing.domain.member.repository.MemberJpaRepository;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import com.sparta.outsourcing.favorite.model.dto.FavoriteRequestDto;
import com.sparta.outsourcing.favorite.model.dto.FavoriteResponseDto;
import com.sparta.outsourcing.favorite.model.entity.Favorite;
import com.sparta.outsourcing.favorite.repository.FavoriteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final FavoriteRepository favoriteRepository;
  private final RestaurantsRepository restaurantsRepository;
  private final MemberJpaRepository memberJpaRepository;

  public FavoriteResponseDto createFavorite(FavoriteRequestDto requestDto) {
    if (!memberJpaRepository.existsById(requestDto.getMemberId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요를 등록할 유저를 찾을 수 없습니다.");
    }

    if (!restaurantsRepository.existsById(requestDto.getRestaurantId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요를 등록할 가게를 찾을 수 없습니다.");
    }

    Favorite favorite = new Favorite();
    favorite.setMemberId(requestDto.getMemberId());
    favorite.setRestaurantId(requestDto.getRestaurantId());
    favorite.setCreatedDate(LocalDate.now());

    Favorite savedFavorite = favoriteRepository.save(favorite);
    return new FavoriteResponseDto(savedFavorite.getId(), savedFavorite.getMemberId(),
        savedFavorite.getRestaurantId(), savedFavorite.getCreatedDate());
  }

  public List<FavoriteResponseDto> findAllFavoritesByMemberId(Long memberId) {
    if (!memberJpaRepository.existsById(memberId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요를 등록한 유저를 찾을 수 없습니다.");
    }

    List<Favorite> favorites = favoriteRepository.findAllByMemberId(memberId);
    if (favorites.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 등록한 좋아요가 없습니다.");
    }

    return favorites.stream()
        .map(favorite -> new FavoriteResponseDto(favorite.getId(),
            favorite.getMemberId(),
            favorite.getRestaurantId(),
            favorite.getCreatedDate()))
        .collect(Collectors.toList());
  }

  public List<FavoriteResponseDto> findFavoritesByRestaurantId(Long restaurantId, Long memberId) {
    if (!memberJpaRepository.existsById(memberId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.");
    }

    if (!restaurantsRepository.existsById(restaurantId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다.");
    }
    List<Favorite> favorites = favoriteRepository.findByRestaurantIdAndMemberId(restaurantId,
        memberId);
    if (favorites.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "등록된 좋아요가 없습니다.");
    }
    return favorites.stream()
        .map(favorite -> new FavoriteResponseDto(
            favorite.getId(),
            favorite.getMemberId(),
            favorite.getRestaurantId(),
            favorite.getCreatedDate()))
        .collect(Collectors.toList());
  }

  public void deleteFavorite(Long memberId, Long restaurantId) {
    Favorite favorite = favoriteRepository.findByMemberIdAndRestaurantId(memberId, restaurantId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "유저나 가게에서 등록된 좋아요를 찾을 수 없습니다."));
    favoriteRepository.delete(favorite);
  }

}
