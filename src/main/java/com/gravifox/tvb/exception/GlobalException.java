package com.gravifox.tvb.exception;

import com.gravifox.tvb.domain.member.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final String value;
    private final ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.value = null;
        this.errorCode = errorCode;

    }
    public GlobalException(ErrorCode errorCode, String value) {
        super(errorCode.getMessage());
        this.value = value;
        this.errorCode = errorCode;
    }

}
