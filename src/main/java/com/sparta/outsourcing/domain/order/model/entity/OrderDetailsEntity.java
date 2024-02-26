package com.sparta.outsourcing.domain.order.model.entity;

import com.sparta.outsourcing.domain.common.entity.Timestamped;
import com.sparta.outsourcing.domain.order.model.OrderDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orderDetails")
public class OrderDetailsEntity extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long orderId;

  @Column(nullable = false)
  private Long menuId;

  @Column(nullable = false)
  private int count;

  public static OrderDetailsEntity of(Long orderId, Long menuId, int count) {

    return OrderDetailsEntity.builder()
        .orderId(orderId)
        .menuId(menuId)
        .count(count)
        .build();
  }

  public OrderDetails toModel() {

    return OrderDetails.builder()
        .id(id)
        .orderId(orderId)
        .menuId(menuId)
        .count(count)
        .build();
  }
}
