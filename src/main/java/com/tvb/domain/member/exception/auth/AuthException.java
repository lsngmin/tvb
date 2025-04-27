package com.tvb.domain.member.exception.auth;

import com.tvb.domain.member.exception.common.ErrorCode;
import com.tvb.exception.GlobalException;
import lombok.Getter;

@Getter
public class AuthException extends GlobalException {
    private final ErrorCode errorCode;

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
