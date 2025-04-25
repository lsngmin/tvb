package com.tvb.domain.member.exception;

import com.tvb.domain.member.exception.auth.AuthException;
import com.tvb.domain.member.exception.common.ErrorCode;

public class InvalidCredentialsException extends AuthException {
    public InvalidCredentialsException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
