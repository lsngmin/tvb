package com.tvb.domain.member.exception.register;

import com.tvb.domain.member.exception.common.AuthException;

import static com.tvb.domain.member.exception.common.ErrorCode.INVALID_USERID_ERROR;

public class InvalidUserIdFormatException extends AuthException {
    public InvalidUserIdFormatException() {
        super(INVALID_USERID_ERROR);
    }
}
