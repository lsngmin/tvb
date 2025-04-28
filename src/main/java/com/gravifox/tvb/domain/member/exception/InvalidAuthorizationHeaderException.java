package com.gravifox.tvb.domain.member.exception;

import com.gravifox.tvb.domain.member.exception.auth.AuthException;
import com.gravifox.tvb.domain.member.exception.common.ErrorCode;

public class InvalidAuthorizationHeaderException extends AuthException {
    public InvalidAuthorizationHeaderException() {

        super(ErrorCode.INVALID_AUTHORIZATION_HEADER);
    }
}
