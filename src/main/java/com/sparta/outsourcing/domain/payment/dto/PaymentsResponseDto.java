package com.sparta.outsourcing.domain.payment.dto;

import com.sparta.outsourcing.domain.payment.entity.Payments;
import java.time.LocalDateTime;
import lombok.Getter;
@Getter
public class PaymentsResponseDto {
  private Long id;
  private Long orderId;
  private Integer totalPrice;
  private LocalDateTime createdDate;
  private LocalDateTime deletedDate;

  public PaymentsResponseDto(Payments payments){
    this.id = payments.getId();
    this.orderId = payments.getOrderId();
    this.totalPrice = payments.getTotalPrice();
    this.createdDate = payments.getCreatedDate();
    this.deletedDate = payments.getDeletedDate();
  }

}
