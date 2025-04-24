package com.tvb.domain.member.exception;

import com.tvb.domain.member.exception.common.AuthException;
import com.tvb.domain.member.exception.common.ErrorCode;
import com.tvb.domain.member.exception.common.ErrorMessageMap;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalMemberExceptionHandler {
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorMessageMap> handleAuthException(AuthException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(
                new ErrorMessageMap(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                )
        );
    }
    //아래부터 회원가입 예외처리 핸들러 입니다.
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorMessageMap> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        StringBuilder errorMessages = new StringBuilder();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            errorMessages.append(error.getDefaultMessage()).append(" ");
//        });
//        String message = errorMessages.toString().trim();
//        return ResponseEntity.status(ErrorCode.REQUEST_VALIDATION_ERROR.getHttpStatus()).body(
//                new ErrorMessageMap(
//                        ErrorCode.REQUEST_VALIDATION_ERROR.getCode(),
//                        message.isEmpty() ? ErrorCode.REQUEST_VALIDATION_ERROR.getMessage() : message
//                )
//        );
//    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageMap> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(ErrorCode.INVALID_LOGINTYPE_ERROR.getHttpStatus()).body(
                new ErrorMessageMap(
                        ErrorCode.INVALID_LOGINTYPE_ERROR.getCode(),
                        ErrorCode.INVALID_LOGINTYPE_ERROR.getMessage()
                )
        );
    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleException(Exception e) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", "서버 오류가 발생했습니다.");
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
