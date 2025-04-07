package com.tvb.domain.member.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "Oops! Something doesn’t match."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),
    REGISTRATION_FAILURE(HttpStatus.UNPROCESSABLE_ENTITY, "INVALID_USER_REQUEST", "Registration failed."),

    INVALID_AUTHORIZATION_HEADER(HttpStatus.UNAUTHORIZED, "INVALID_AUTHORIZATION_HEADER", "Authorization header must start with 'Bearer'."),

    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN_NOT_FOUND", "Token Not Found.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }


}
