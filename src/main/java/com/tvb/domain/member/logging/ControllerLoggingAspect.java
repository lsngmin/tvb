package com.tvb.domain.member.logging;

import com.tvb.domain.member.dto.register.RegisterRequestData;
import com.tvb.domain.member.dto.register.RegisterResponse;
import com.tvb.domain.member.exception.common.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class ControllerLoggingAspect {
    @Pointcut("execution(* com.tvb.domain.member.controller.RegisterController.*(..))")
    public void registerController() {}

    @AfterThrowing(pointcut = "registerController()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, AuthException ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.warn("\"{} {}\" {} - [{}] {}", request.getMethod(), request.getRequestURI(), ex.getErrorCode().getHttpStatus(), ex.getClass().getSimpleName(), ex.getMessage());
    }
    @AfterReturning(pointcut = "registerController()", returning = "response")
    public void afterReturning(ResponseEntity<RegisterResponse> response) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info(formatMessage(
                "UserRegistration", "RequestCompleted", "Email",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatusCode().value(),
                maskedId(response.getBody().getUserId())
        ));
    }
    @Before("registerController()")
    public void before(JoinPoint joinPoint) {
        RegisterRequestData requestData = (RegisterRequestData) joinPoint.getArgs()[0];
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info(formatMessage(
                "UserRegistration", "RequestReceived", "Email",
                request.getMethod(),
                request.getRequestURI(),
                "",
                maskedId(requestData.getUser().getUserId())
        ));
    }


    /**
     *
     * @param a
     * @param s
     * @param d
     * @param args
     * @return
     */
    private static String formatMessage(String a, String s, String d, Object... args) {
        String f = String.format("\"%s %s\" %3s - %s | %-16s | %s %s",
                args[0], args[1], args[2], a, s, d, args[3]
                );
        return f;
    }

    private static String maskedId(String s) {
        char[] c = s.toCharArray();
        for(int i = 3; i < c.length; i++) {
            if(c[i] == '@') break;
            c[i] = '*';
        }
        return new String(c);
    }
}
