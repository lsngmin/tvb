package com.tvb.domain.member.logging;

import com.tvb.annotation.LogContext;
import com.tvb.domain.member.dto.AuthDTO;
import com.tvb.domain.member.exception.register.RegisterException;
import com.tvb.domain.member.logging.util.LogFactory;
import com.tvb.domain.member.logging.util.LogStatus;
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
    private final LogFactory logFactory;

    @Before("@annotation(logContext)")
    public void before(JoinPoint joinPoint, LogContext logContext) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        AuthDTO authDTO = (AuthDTO) joinPoint.getArgs()[0];
        String userId = authDTO.extractUserID();

        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.info(logFactory.of(request, logContext, className).status(LogStatus.PENDING).value(userId).build());
    }

    @AfterReturning(pointcut = "@annotation(logContext)", returning = "response")
    public void afterReturning(JoinPoint joinPoint, LogContext logContext, ResponseEntity<? extends AuthDTO> response) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        String userId = response.getBody().extractUserID();

        log.info(logFactory.of(request, response, logContext, className).status(LogStatus.OK).value(userId).build());
    }

    @AfterThrowing(pointcut = "@annotation(logContext)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, RegisterException ex, LogContext logContext) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.warn(logFactory.of(request, ex, logContext, className).status(ex.getErrorCode()).value(ex.getValue()).build());
    }
}