package com.sparta.outsourcing.domain.payment.service;

import com.sparta.outsourcing.domain.payment.dto.PaymentsRequestDto;
import com.sparta.outsourcing.domain.payment.dto.PaymentsResponseDto;
import com.sparta.outsourcing.domain.payment.entity.Payments;
import com.sparta.outsourcing.domain.payment.repository.PaymentsRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentsService {

  private final PaymentsRepository paymentsRepository;

  public PaymentsResponseDto createPayment(PaymentsRequestDto paymentsRequestDto) {

    int payment = paymentsRequestDto.getPrice() * paymentsRequestDto.getCount();
    Payments payments = Payments.builder().count(paymentsRequestDto.getCount())
        .price(paymentsRequestDto.getPrice()).totalPrice(payment).createdDate(LocalDateTime.now()).orderId(1L).build();
    Payments savedPayments = paymentsRepository.save(payments);
    PaymentsResponseDto paymentsResponseDto = new PaymentsResponseDto(savedPayments);

    return paymentsResponseDto;

  }

  public PaymentsResponseDto getPayment(Long paymentId) {
    Payments payments = findByPaymentId(paymentId);
    PaymentsResponseDto paymentsResponseDto = new PaymentsResponseDto(payments);
    return paymentsResponseDto;
  }

  public List<PaymentsResponseDto> getPaymentList() {
    return paymentsRepository.findAllByOrderByPaymentId().stream()
        .map(PaymentsResponseDto::new).toList();
  }

  public Long deletePayment(Long paymentId){

    deleteByPaymentId(paymentId);
      return paymentId;
  }


  private Payments findByPaymentId(Long paymentId) {
    return paymentsRepository.findById(paymentId)
        .orElseThrow(() -> new IllegalArgumentException("결제 정보가 존재하지 않습니다"));
  }
  private void deleteByPaymentId(Long paymentId){

    Optional<Payments>paymentOptional= paymentsRepository.findById(paymentId);

    if (paymentOptional.isEmpty()){
      throw new IllegalArgumentException("취소할 결제 정보가 존재하지 않습니다");
    }
    else{
      paymentsRepository.deleteById(paymentId);
    }
  }


}
