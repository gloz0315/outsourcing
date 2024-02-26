package com.sparta.outsourcing.domain.order.service;

import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.basket.repository.BasketRepository;
import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.order.model.Order;
import com.sparta.outsourcing.domain.order.model.OrderDetails;
import com.sparta.outsourcing.domain.order.model.OrderType;
import com.sparta.outsourcing.domain.order.model.entity.OrderDetailsEntity;
import com.sparta.outsourcing.domain.order.model.entity.OrderEntity;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.order.service.dto.MenuInfoDto;
import com.sparta.outsourcing.domain.order.service.dto.OrderInfoResponse;
import com.sparta.outsourcing.domain.order.service.dto.OrderResponseDto;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final BasketRepository basketRepository;
  private final RestaurantsRepository restaurantsRepository;

  // 주문하는 메서드
  @Transactional
  public OrderResponseDto order(UserDetails userDetails) {
    Member member = memberRepository.findMemberOrElseThrow(userDetails.getUsername());

    List<Basket> basketList = basketRepository.basketInfo(member.getId());

    if (basketList.isEmpty()) {
      throw new IllegalArgumentException("장바구니가 비어있습니다.");
    }

    if (!isSingleRestaurantOrder(basketList)) {
      throw new IllegalArgumentException("주문은 하나의 가게에만 할 수 있습니다.");
    }

    Long restaurantId = basketList.get(0).getRestaurantId();

    // 후에 주문이 되었으면 스케줄링으로 통해서 정해진 시간에 주문 상태를 변경할 예정
    Long orderId = orderRepository.registerOrder(member.getId(), restaurantId);
    orderRepository.registerOrderDetails(orderId, basketList);
    // 후에 결제에 대한 레파지토리도 필요함 -> 장바구니 안의 정보를 order 쪽으로 넘어갔으니, 결제 금액도 데이터베이스에 남겨야하기 때문
    basketRepository.deleteBasket(member.getId());

    return OrderResponseDto.builder()
        .memberId(member.getId())
        .orderId(orderId)
        .build();
  }

  // 주문 조회 -> 유저 아이디, 오더 아이디, 가게 이름, 주문 상태, 주문 내역들
  public OrderInfoResponse orderInfo(Long orderId) {
    Order order = orderRepository.findByOrderId(orderId).toModel();

    List<OrderDetails> orderDetailsList = orderRepository.findOrderDetailsByOrderId(orderId)
        .stream().map(OrderDetailsEntity::toModel).toList();

    String restaurantName = restaurantsRepository.findById(order.getRestaurantId()).orElseThrow(
        () -> new EntityNotFoundException("해당 가게가 존재하지 않습니다.")
    ).getName();

    List<MenuInfoDto> menuInfoDtoList = orderDetailsList.stream().map(
        orderDetails -> new MenuInfoDto(orderDetails.getMenuId(), orderDetails.getCount())
    ).toList();

    return OrderInfoResponse.builder()
        .orderId(orderId)
        .memberId(order.getMemberId())
        .restaurantName(restaurantName)
        .orderStatus(order.getOrderStatus())
        .menuInfoDtoList(menuInfoDtoList)
        .build();
  }

  @Transactional
  public void orderDelete(Long orderId) {
    OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
    OrderType orderStatus = orderEntity.getOrderStatus();

    if(orderStatus.equals(OrderType.DELIVERY)) {
      throw new IllegalArgumentException("현재 배달 중이므로 결제를 취소할 수 없습니다.");
    }

    orderRepository.updatedCancel(orderId);
    orderRepository.deleteOrderAll(orderId);
    //payments도 삭제해야함.
  }

  // 한 가게에만 주문했는지 체크해주는 메서드
  private boolean isSingleRestaurantOrder(List<Basket> basketList) {
    if (basketList.size() == 1) {
      return true;
    }

    for (int i = 1; i < basketList.size(); i++) {
      Basket prevBasket = basketList.get(i - 1);
      Basket currentBasket = basketList.get(i);

      if (!prevBasket.checkRestaurantId(currentBasket.getRestaurantId())) {
        return false;
      }
    }
    return true;
  }
}
