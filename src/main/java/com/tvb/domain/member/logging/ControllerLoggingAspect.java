package com.tvb.domain.member.logging;

import com.tvb.domain.member.dto.register.RegisterRequestData;
import com.tvb.domain.member.dto.register.RegisterResponse;
import com.tvb.domain.member.exception.common.RegisterException;
import com.tvb.domain.member.exception.register.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@Aspect
public class ControllerLoggingAspect  {
    @Pointcut("execution(* com.tvb.domain.member.controller.RegisterController.*(..))")
    public void myPointcut() {}

    @AfterThrowing(pointcut = "myPointcut()", throwing = "ex")
    public void afterThrowing(RegisterException ex) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            log.warn(LoggingUtil.formatMessage(
                    "UserRegistration",
                    ex.getClass().getSimpleName(),
                    ex.getMessage(),
                    request.getMethod(),
                    request.getRequestURI(),
                    ex.getErrorCode().getHttpStatus().value(),
                    LoggingUtil.maskValue(((InvalidFormatException) ex).getRejectedValue())
            ));
    }
    @AfterReturning(pointcut = "myPointcut()", returning = "response")
    public void afterReturning(ResponseEntity<RegisterResponse> response) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info(LoggingUtil.formatMessage(
                "UserRegistration", "RequestCompleted", "Email",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatusCode().value(),
                LoggingUtil.maskValue(response.getBody().getUserId())
        ));
    }
    @Before("myPointcut()")
    public void before(JoinPoint joinPoint) {
        RegisterRequestData requestData = (RegisterRequestData) joinPoint.getArgs()[0];
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info(LoggingUtil.formatMessage(
                "UserRegistration", "RequestReceived", "Email",
                request.getMethod(),
                request.getRequestURI(),
                "",
                LoggingUtil.maskValue(requestData.getUser().getUserId())
        ));
    }
}
