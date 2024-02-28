package com.sparta.outsourcing.domain.order.service;

import static com.sparta.outsourcing.global.exception.CustomError.EMPTY_BASKET;
import static com.sparta.outsourcing.global.exception.CustomError.NOT_CANCEL_ORDER;
import static com.sparta.outsourcing.global.exception.CustomError.NOT_CONTAIN_MENU;
import static com.sparta.outsourcing.global.exception.CustomError.NOT_EXIST_PAYMENT;
import static com.sparta.outsourcing.global.exception.CustomError.RESTAURANT_NOT_EXIST;

import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.basket.repository.BasketRepository;
import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.repository.member.MemberRepository;
import com.sparta.outsourcing.domain.menu.model.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.order.model.Order;
import com.sparta.outsourcing.domain.order.model.OrderDetails;
import com.sparta.outsourcing.domain.order.model.OrderType;
import com.sparta.outsourcing.domain.order.model.entity.OrderDetailsEntity;
import com.sparta.outsourcing.domain.order.model.entity.OrderEntity;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.order.service.dto.MenuInfoDto;
import com.sparta.outsourcing.domain.order.service.dto.OrderInfoResponse;
import com.sparta.outsourcing.domain.order.service.dto.OrderResponseDto;
import com.sparta.outsourcing.domain.payment.entity.Payments;
import com.sparta.outsourcing.domain.payment.repository.PaymentsJpaRepository;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import com.sparta.outsourcing.global.exception.CustomException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final BasketRepository basketRepository;
  private final RestaurantsRepository restaurantsRepository;
  private final MenuRepository menuRepository;
  private final PaymentsJpaRepository paymentsJpaRepository;

  public OrderResponseDto order(UserDetails userDetails) {
    Member member = memberRepository.findMemberOrElseThrow(userDetails.getUsername());

    List<Basket> basketList = basketRepository.basketInfo(member.getId());

    if (basketList.isEmpty()) {
      throw new CustomException(EMPTY_BASKET);
    }

    if (!isSingleRestaurantOrder(basketList)) {
      throw new CustomException(NOT_CONTAIN_MENU);
    }

    Long restaurantId = basketList.get(0).getRestaurantId();

    Long orderId = orderRepository.registerOrder(member.getId(), restaurantId);
    orderRepository.registerOrderDetails(orderId, basketList);

    registerPayment(basketList, restaurantId, orderId);

    basketRepository.deleteBasket(member.getId());

    return OrderResponseDto.builder()
        .memberId(member.getId())
        .orderId(orderId)
        .build();
  }

  @Transactional(readOnly = true)
  public OrderInfoResponse orderInfo(Long orderId) {
    Order order = orderRepository.findByOrderId(orderId).toModel();

    List<OrderDetails> orderDetailsList = orderRepository.findOrderDetailsByOrderId(orderId)
        .stream().map(OrderDetailsEntity::toModel).toList();

    String restaurantName = restaurantsRepository.findById(order.getRestaurantId()).orElseThrow(
        () -> new CustomException(RESTAURANT_NOT_EXIST)
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

  public void orderDelete(Long orderId) {
    OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
    OrderType orderStatus = orderEntity.getOrderStatus();

    if (orderStatus.equals(OrderType.DELIVERY)) {
      throw new CustomException(NOT_CANCEL_ORDER);
    }

    orderRepository.updatedCancel(orderId);
    orderRepository.deleteOrderAll(orderId);

    Payments payments = paymentsJpaRepository.findPaymentsByOrderId(orderId).orElseThrow(
        () -> new CustomException(NOT_EXIST_PAYMENT)
    );
    paymentsJpaRepository.delete(payments);
  }

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

  private void registerPayment(List<Basket> basketList, Long restaurantId, Long orderId) {
    int totalPrice = 0;

    for (Basket basket : basketList) {
      Menu menu = menuRepository.findByMenu(restaurantId, basket.getMenuId());
      totalPrice += basket.getCount() * menu.getPrice();
    }

    Payments payments = Payments.builder()
        .orderId(orderId)
        .totalPrice(totalPrice)
        .createdDate(LocalDateTime.now())
        .build();

    paymentsJpaRepository.save(payments);
  }

}
