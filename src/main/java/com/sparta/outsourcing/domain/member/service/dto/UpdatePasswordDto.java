package com.sparta.outsourcing.domain.member.service.dto;

import com.sparta.outsourcing.domain.member.controller.dto.UpdatePasswordRequestDto;
import com.sparta.outsourcing.global.exception.CustomError;
import com.sparta.outsourcing.global.exception.CustomException;
import lombok.Getter;

@Getter
public class UpdatePasswordDto {

  private final String changePassword;
  private final String currentPassword;
  private final String reCurrentPassword;

  public UpdatePasswordDto(UpdatePasswordRequestDto dto) {
    changePassword = dto.getChangePassword();
    currentPassword = dto.getCurrentPassword();
    reCurrentPassword = dto.getReCurrentPassword();
  }

  public void checkChangePasswordEquals() {
    if (!currentPassword.equals(reCurrentPassword)) {
      throw new CustomException(CustomError.CHANGE_PASSWORD_ERROR);
    }
  }
}
