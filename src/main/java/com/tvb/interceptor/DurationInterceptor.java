package com.tvb.interceptor;

import com.tvb.domain.member.exception.register.InvalidFormatException;
import com.tvb.domain.member.exception.register.RegisterException;
import com.tvb.domain.member.logging.util.LoggingUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class DurationInterceptor implements HandlerInterceptor {
    private final LoggingUtil loggingUtil;
    private static final String START_TIME = "startTime";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long startTime = (Long) request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis();
        String duration = endTime - startTime + "ms";

        log.info(loggingUtil.formatMessage(
                "RequestDuration ", "TrackingCompleted", "Duration",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration
        ));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);
        return true;
    }
}
