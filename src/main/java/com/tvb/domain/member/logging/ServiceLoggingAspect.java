package com.tvb.domain.member.logging;

import com.tvb.domain.member.dto.register.RegisterRequestData;
import com.tvb.domain.member.exception.auth.AuthException;
import com.tvb.domain.member.logging.util.LoggingUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceLoggingAspect {
    private final LoggingUtil loggingUtil;

    @Pointcut("execution(* com.tvb.domain.member.service.impl.RegisterServiceImpl.*(..))")
    public void myPointcut() {}

    @AfterThrowing(pointcut = "myPointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, AuthException ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RegisterRequestData registerRequestData = (RegisterRequestData) joinPoint.getArgs()[0];

        log.warn(loggingUtil.formatMessage(
                "UserRegistration",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getMethod(),
                request.getRequestURI(),
                ex.getErrorCode().getHttpStatus().value(),
                loggingUtil.maskValue(registerRequestData.getUser().getUserId())
        ));
    }
}
