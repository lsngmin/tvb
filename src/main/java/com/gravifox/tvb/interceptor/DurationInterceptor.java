package com.gravifox.tvb.interceptor;

import com.gravifox.tvb.domain.member.logging.util.LogFactory;
import com.gravifox.tvb.domain.member.logging.util.LogStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class DurationInterceptor implements HandlerInterceptor {
    private final LogFactory logFactory;
    private static final String START_TIME = "startTime";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String className = handlerMethod.getBeanType().getSimpleName();

        Long startTime = (Long) request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis();
        String duration = "Duration: " + (endTime - startTime) + "ms";

        log.info(logFactory.of(request, response, className)
                .action("RequestDuration")
                .status(LogStatus.OK)
                        .detail(duration)
                .build());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);
        return true;
    }
}
