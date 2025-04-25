package com.tvb.domain.member.exception.register;

import com.tvb.domain.member.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public class RegisterException extends RuntimeException {
  private final ErrorCode errorCode;

  public RegisterException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
