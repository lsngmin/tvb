package com.tvb.infra.logging;

import com.tvb.domain.log.UserRequestLogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Order(1)
public class LoggingFilter extends OncePerRequestFilter {

    private final RequestLogService requestLogService; // ✅ 주입 필요!

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isBlank()) {
            ipAddress = request.getRemoteAddr();
        }

        String requestId = request.getHeader("X-RequestID");
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }

        String userAgent = request.getHeader("User-Agent");
        String trimmedUserAgent = userAgent != null && userAgent.contains("Chrome/")
                ? userAgent.substring(userAgent.indexOf("Chrome/")).split(" ")[0]
                : userAgent;
        String domain = request.getServerName();
        String platform = request.getHeader("sec-ch-ua-platform");
        String language = request.getHeader("Accept-Language");
        String primaryLanguage = language != null ? language.split(",")[0] : null;

        // 🧩 MDC 저장 (로그 출력용)
        MDC.put("ipAddress", ipAddress);
        MDC.put("requestId", requestId);
        MDC.put("userAgent", trimmedUserAgent);
        MDC.put("domain", domain);
        if (platform != null) MDC.put("platform", platform);
        if (language != null) MDC.put("language", primaryLanguage);

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            requestLogService.saveLog(request, response, duration);

            MDC.clear();
        }
    }
}
