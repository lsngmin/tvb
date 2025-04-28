package com.gravifox.tvb.domain.member.logging.util;

import com.gravifox.tvb.annotation.LogContext;
import com.gravifox.tvb.domain.member.exception.common.ErrorCode;
import com.gravifox.tvb.exception.GlobalException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LogFactory {
    private final LoggingUtil loggingUtil;

    @Autowired
    public LogFactory(LoggingUtil loggingUtil) {
        this.loggingUtil = loggingUtil;
    }

    public LogBuilder of(HttpServletRequest request, LogContext logContext, String className) {
        return new LogBuilder(request, (ResponseEntity<?>) null, logContext, className, loggingUtil);
    }
    public LogBuilder of(HttpServletRequest request, ResponseEntity<?> response, LogContext logContext, String className) {
        return new LogBuilder(request, response, logContext, className, loggingUtil);
    }
    public LogBuilder of(HttpServletRequest request, GlobalException ex, LogContext logContext, String className) {
        return new LogBuilder(request, ex, logContext, className, loggingUtil);
    }
    public LogBuilder of(HttpServletRequest request, HttpServletResponse response, String className) {
        return new LogBuilder(request, response, className, loggingUtil);
    }

    public static class LogBuilder {
        private final LogMessage.LogMessageBuilder builder;
        private final LoggingUtil loggingUtil;

        public LogBuilder(HttpServletRequest request, HttpServletResponse response, String className, LoggingUtil loggingUtil) {
            String statusCode = response != null ? Integer.toString(response.getStatus()) : "";

            this.builder = LogMessage.builder()
                    .method(request.getMethod())
                    .uri(request.getRequestURI())
                    .code(statusCode)
                    .className(className);

            this.loggingUtil = loggingUtil;
        }

        public LogBuilder(HttpServletRequest request,
                          GlobalException e,
                          LogContext logContext,
                          String className,
                          LoggingUtil loggingUtil) {

            this.builder = LogMessage.builder()
                    .method(request.getMethod())
                    .uri(request.getRequestURI())
                    .code(Integer.toString(e.getErrorCode().getHttpStatus().value()))
                    .action(logContext.action())
                    .status(logContext.status())
                    .detail(e.getClass().getSimpleName())
                    .className(className);

            this.loggingUtil = loggingUtil;

        }

        public LogBuilder(HttpServletRequest request,
                          ResponseEntity<?> response,
                          LogContext logContext,
                          String className,
                          LoggingUtil loggingUtil) {
            String statusCode = response != null ? Integer.toString(response.getStatusCode().value()) : "";

            this.builder = LogMessage.builder()
                    .method(request.getMethod())
                    .uri(request.getRequestURI())
                    .code(statusCode)
                    .action(logContext.action())
                    .status(logContext.status())
                    .detail(logContext.detail())
                    .className(className);
            this.loggingUtil = loggingUtil;
        }

        public LogBuilder className(String c) {builder.className(c); return this;}
        public LogBuilder method(String m) { builder.method(m); return this; }
        public LogBuilder uri(String u) { builder.uri(u); return this; }
        public LogBuilder status(LogStatus s) { builder.status(s.getStatus()); return this; }
        public LogBuilder status(ErrorCode e) { builder.status(e.getCode()); return this; }

        public LogBuilder detail(String d) { builder.detail(d); return this; }
        public LogBuilder value(String v) { builder.value(mask(v)); return this; }
        public LogBuilder action(String a) { builder.action(a); return this; }
        public LogBuilder code(String c) { builder.action(c); return this; }

        public String build() {
            return builder.build().formatMessage();
        }

        private String mask(String v) {
            return loggingUtil.maskValue(v);
        }
    }
}
