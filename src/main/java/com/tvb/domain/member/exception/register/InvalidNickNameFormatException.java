package com.tvb.domain.member.exception.register;

import com.tvb.domain.member.exception.common.AuthException;

import static com.tvb.domain.member.exception.common.ErrorCode.INVALID_NICKNAME_ERROR;

public class InvalidNickNameFormatException extends AuthException {
    public InvalidNickNameFormatException() {
        super(INVALID_NICKNAME_ERROR);
    }
}
