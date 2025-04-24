package com.tvb.domain.member.exception.register;

import com.tvb.domain.member.exception.common.AuthException;
import com.tvb.domain.member.exception.common.ErrorCode;

public class InvalidPasswordFormatException extends AuthException {
    public InvalidPasswordFormatException() {
        super(ErrorCode.INVALID_PASSWORD_ERROR);
    }
}
