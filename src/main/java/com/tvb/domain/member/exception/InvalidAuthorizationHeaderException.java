package com.tvb.domain.member.exception;

import com.tvb.domain.member.exception.auth.AuthException;
import com.tvb.domain.member.exception.common.ErrorCode;

public class InvalidAuthorizationHeaderException extends AuthException {
    public InvalidAuthorizationHeaderException() {

        super(ErrorCode.INVALID_AUTHORIZATION_HEADER);
    }
}
