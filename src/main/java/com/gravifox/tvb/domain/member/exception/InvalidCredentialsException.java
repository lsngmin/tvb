package com.gravifox.tvb.domain.member.exception;

import com.gravifox.tvb.domain.member.exception.auth.AuthException;
import com.gravifox.tvb.domain.member.exception.common.ErrorCode;

public class InvalidCredentialsException extends AuthException {
    public InvalidCredentialsException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
