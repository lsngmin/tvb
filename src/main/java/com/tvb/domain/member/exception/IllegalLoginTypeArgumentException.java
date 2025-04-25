package com.tvb.domain.member.exception;

import com.tvb.domain.member.exception.auth.AuthException;
import com.tvb.domain.member.exception.common.ErrorCode;

public class IllegalLoginTypeArgumentException extends AuthException {
    public IllegalLoginTypeArgumentException() {
        super(ErrorCode.REGISTRATION_FAILURE);
    }
}
