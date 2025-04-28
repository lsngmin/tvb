package com.gravifox.tvb.domain.member.exception.register;

import com.gravifox.tvb.domain.member.exception.common.ErrorCode;

import static com.gravifox.tvb.domain.member.exception.common.ErrorCode.*;

public class DataIntegrityViolationException extends RegisterException {
    public static DataIntegrityViolationException forDuplicateUserId(String n) {
        return new DataIntegrityViolationException(DUPLICATE_USER_ID, n);
    }

    public DataIntegrityViolationException(ErrorCode e) {super(e);}
    public DataIntegrityViolationException(ErrorCode e, String o) {super(e, o);}

    public DataIntegrityViolationException() {
        super(ErrorCode.REGISTRATION_FAILURE);
    }
}
