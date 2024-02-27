package com.sparta.outsourcing.domain.basket.service;

import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.basket.repository.BasketRepository;
import com.sparta.outsourcing.domain.basket.service.dto.BasketRequestDto;
import com.sparta.outsourcing.domain.basket.service.dto.BasketResponseDto;
import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasketService {

  private final BasketRepository basketRepository;
  private final MemberRepository memberRepository;
  private final RestaurantsRepository restaurantsRepository;
  // 메뉴 레파지토리 또한 설정해야함

  @Transactional
  public BasketResponseDto inputBasket(UserDetails userDetails, BasketRequestDto dto) {
    Member member = memberRepository.findMemberOrElseThrow(userDetails.getUsername());
    restaurantsRepository.findById(dto.getRestaurantId()).orElseThrow(
        () -> new EntityNotFoundException("해당 가게 정보가 없습니다.")
    );
    // 메뉴에 대한 정보도 조회해야함. (있는지 없는지)
    Basket basket = Basket.builder()
        .memberId(member.getId())
        .restaurantId(dto.getRestaurantId())
        .menuId(dto.getMenuId())
        .count(dto.getCount())
        .build();

    basketRepository.register(basket);

    return BasketResponseDto.builder()
        .menuId(basket.getMenuId())
        .memberId(basket.getMemberId())
        .restaurantId(basket.getRestaurantId())
        .count(basket.getCount())
        .build();
  }

  public List<BasketResponseDto> getBasketInfo(UserDetails userDetails) {
    Member member = memberRepository.findMemberOrElseThrow(userDetails.getUsername());

    List<Basket> basketList = basketRepository.basketInfo(member.getId());

    if(basketList.isEmpty()) {
      return null;
    }

    return basketList.stream().map(
        basket -> BasketResponseDto.builder()
            .restaurantId(basket.getRestaurantId())
            .menuId(basket.getMenuId())
            .count(basket.getCount())
            .build()
    ).toList();
  }
}
