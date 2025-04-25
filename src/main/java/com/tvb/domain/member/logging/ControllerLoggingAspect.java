package com.tvb.domain.member.logging;

import com.tvb.domain.member.dto.register.RegisterRequestData;
import com.tvb.domain.member.dto.register.RegisterResponse;
import com.tvb.domain.member.exception.register.RegisterException;
import com.tvb.domain.member.exception.register.InvalidFormatException;
import com.tvb.domain.member.logging.util.LoggingUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ControllerLoggingAspect  {
    private final LoggingUtil loggingUtil;

    @Pointcut("execution(* com.tvb.domain.member.controller.RegisterController.*(..))")
    public void myPointcut() {}

    @AfterThrowing(pointcut = "myPointcut()", throwing = "ex")
    public void afterThrowing(RegisterException ex) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            log.warn(loggingUtil.formatMessage(
                    "UserRegistration",
                    ex.getClass().getSimpleName(),
                    ex.getMessage(),
                    request.getMethod(),
                    request.getRequestURI(),
                    ex.getErrorCode().getHttpStatus().value(),
                    loggingUtil.maskValue(((InvalidFormatException) ex).getRejectedValue())
            ));
    }
    @AfterReturning(pointcut = "myPointcut()", returning = "response")
    public void afterReturning(ResponseEntity<RegisterResponse> response) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info(loggingUtil.formatMessage(
                "UserRegistration", "RequestCompleted", "Email",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatusCode().value(),
                loggingUtil.maskValue(response.getBody().getUserId())
        ));
    }
    @Before("myPointcut()")
    public void before(JoinPoint joinPoint) {
        RegisterRequestData requestData = (RegisterRequestData) joinPoint.getArgs()[0];
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info(loggingUtil.formatMessage(
                "UserRegistration", "RequestReceived", "Email",
                request.getMethod(),
                request.getRequestURI(),
                "",
                loggingUtil.maskValue(requestData.getUser().getUserId())
        ));
    }
}