package com.gravifox.tvb.domain.member.exception;

import com.gravifox.tvb.domain.member.exception.auth.AuthException;
import com.gravifox.tvb.domain.member.exception.common.ErrorCode;

import static com.gravifox.tvb.domain.member.exception.common.ErrorCode.*;

public class DataIntegrityViolationException extends AuthException {
    public static DataIntegrityViolationException forDuplicateUserId() {
        return new DataIntegrityViolationException(DUPLICATE_USER_ID);
    }

    public DataIntegrityViolationException(ErrorCode errorCode) {super(errorCode);}
    public DataIntegrityViolationException() {
        super(ErrorCode.REGISTRATION_FAILURE);
    }
}
