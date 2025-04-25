package com.tvb.domain.member.exception.register;

import com.tvb.domain.member.exception.common.ErrorCode;
import com.tvb.domain.member.exception.common.RegisterException;

import static com.tvb.domain.member.exception.common.ErrorCode.*;

public class InvalidFormatException extends RegisterException {
    private final String rejectedValue;
    public String getRejectedValue() {
        return rejectedValue;
    }

    public static InvalidFormatException forInvalidNickName(String n) {
        return new InvalidFormatException(INVALID_NICKNAME_ERROR, n);
    }
    public static InvalidFormatException forInvalidUserId(String n) {
        return new InvalidFormatException(INVALID_USERID_ERROR, n);
    }
    public static InvalidFormatException forInvalidPassword(String n) {
        return new InvalidFormatException(INVALID_PASSWORD_ERROR, n);
    }
    public static InvalidFormatException forInvalidLoginType(String n) {
        return new InvalidFormatException(INVALID_LOGINTYPE_ERROR, n);
    }

    public InvalidFormatException(ErrorCode e, String o) {
        super(e);
        this.rejectedValue = o;
    }
    public InvalidFormatException() {
        super(ErrorCode.REQUEST_VALIDATION_ERROR);
        this.rejectedValue = "";
    }
}
