package com.tvb.domain.member.logging;

import com.tvb.domain.member.dto.register.RegisterRequestData;
import com.tvb.domain.member.dto.register.module.RegisterUserRequestData;
import com.tvb.domain.member.exception.common.AuthException;
import jakarta.servlet.http.HttpServletRequest;
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
public class LoggingAspect {
    @Pointcut("execution(* com.tvb.domain.member.service.impl.RegisterServiceImpl.*(..))")
    public void registerService() {}

    @AfterThrowing(pointcut = "registerService()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, AuthException ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object[] args = joinPoint.getArgs();
        RegisterRequestData registerRequestData = (RegisterRequestData) args[0];
        String userD_ = registerRequestData.getUser().getUserId();
        log.warn("\"{} {}\" {} - [{}] {}({})", request.getMethod(), request.getRequestURI(), ex.getErrorCode().getHttpStatus(), ex.getClass().getSimpleName(), ex.getMessage(), userD_);
    }
}
