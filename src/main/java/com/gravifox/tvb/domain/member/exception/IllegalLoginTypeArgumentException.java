package com.gravifox.tvb.domain.member.exception;

import com.gravifox.tvb.domain.member.exception.auth.AuthException;
import com.gravifox.tvb.domain.member.exception.common.ErrorCode;

public class IllegalLoginTypeArgumentException extends AuthException {
    public IllegalLoginTypeArgumentException() {
        super(ErrorCode.REGISTRATION_FAILURE);
    }
}
