package com.tvb.domain.member.exception.register;

import com.tvb.domain.member.exception.common.AuthException;

import static com.tvb.domain.member.exception.common.ErrorCode.INVALID_LOGINTYPE_ERROR;

public class InvalidLoginTypeFormatException extends AuthException {
    public InvalidLoginTypeFormatException() {
        super(INVALID_LOGINTYPE_ERROR);
    }
}
