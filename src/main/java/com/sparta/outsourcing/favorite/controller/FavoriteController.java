package com.sparta.outsourcing.favorite.controller;

import com.sparta.outsourcing.favorite.model.dto.FavoriteRequestDto;
import com.sparta.outsourcing.favorite.model.dto.FavoriteResponseDto;
import com.sparta.outsourcing.favorite.service.FavoriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteController {

  private final FavoriteService favoriteService;

  @PostMapping
  public ResponseEntity<FavoriteResponseDto> addFavorite(
      @RequestBody FavoriteRequestDto requestDto) {
    FavoriteResponseDto favoriteResponseDto = favoriteService.createFavorite(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(favoriteResponseDto);
  }

  @GetMapping("/{memberId}")
  public ResponseEntity<List<FavoriteResponseDto>> findAllFavoritesByMember(
      @PathVariable Long memberId) {
    List<FavoriteResponseDto> favorites = favoriteService.findAllFavoritesByMemberId(memberId);
    return ResponseEntity.ok(favorites);
  }

  @GetMapping("/restaurants/{restaurantId}")
  public ResponseEntity<List<FavoriteResponseDto>> findFavoritesByRestaurantId(
      @PathVariable Long restaurantId, @RequestParam Long memberId) {
    List<FavoriteResponseDto> favorites = favoriteService.findFavoritesByRestaurantId(restaurantId,
        memberId);
    return ResponseEntity.ok(favorites);
  }

  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<Void> deleteFavorite(@RequestParam Long memberId,
      @PathVariable Long restaurantId) {
    favoriteService.deleteFavorite(memberId, restaurantId);
    return ResponseEntity.noContent().build();
  }
}
