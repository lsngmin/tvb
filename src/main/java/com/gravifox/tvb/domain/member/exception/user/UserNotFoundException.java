package com.gravifox.tvb.domain.member.exception.user;

import com.gravifox.tvb.domain.member.exception.auth.AuthException;
import com.gravifox.tvb.domain.member.exception.common.ErrorCode;

public class UserNotFoundException extends AuthException {
    public UserNotFoundException(Long userNo) {
        super(ErrorCode.USER_NOT_FOUND, String.valueOf(userNo));
    }
}
