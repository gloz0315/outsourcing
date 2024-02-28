package com.sparta.outsourcing.domain.member.controller.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateRequestDto {

  @Size(max = 20)
  private String nickname;

  @Size(min = 8, max = 15)
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]+$")
  private String password;

  private String address;

  @Size(max = 13)
  private String number;
}
