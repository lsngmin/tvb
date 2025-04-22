package com.tvb.infra.logging.slack.service;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import com.tvb.infra.logging.LoggingFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static com.slack.api.webhook.WebhookPayloads.payload;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
public class SLog {
    @Value("${logging.slack.webhook-uri}")
    private String u;
    private final Slack slack = Slack.getInstance();

    public void info(String m) {
        log.info("staart log m {}",m);
        try{
            HttpServletRequest request = LoggingFilter.getRequest();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            StringBuilder sb = new StringBuilder();
            sb
                    .append(":ballot_box_with_check: Log Message").append("\n")
                    .append("```").append(m).append("```")

                    .append(":white_check_mark: Request URI").append("\n")
                    .append("```").append(request.getMethod()).append(" - ").append(request.getRequestURI()).append("```")
                    .append(":white_check_mark: Request ID").append("\n")
                    .append("```").append(request.getHeader("X-RequestID")).append("```")
                    .append(":white_check_mark: Request Address").append("\n")
                    .append("```").append(request.getHeader("X-Forwarded-For")).append("```");

            StringBuilder sb2 = new StringBuilder();
            sb2
                    .append("*INFO * - ").append(LocalDateTime.now().format(formatter));

            WebhookResponse r = slack.send(u, payload(p -> p
                    .attachments(List.of(
                            Attachment.builder()
                                    .fallback("Slack Error Message")
                                    .pretext(sb2.toString())
                                    .text(sb.toString())
                                    .color("#36a64f")
                                    .build()
                    ))

            ));
            log.info(r.getBody());
            log.info(r.getMessage());
        } catch (IOException error) {
            log.error(error.getMessage());
            log.info("failed to send log message");
    }
    log.info("successfully sent log message");
//    public void send(Exception e) {
//        try {
//            Payload p = Payload.builder().
//                    text(e.getMessage()).
//                    build();
//            slack.send(u, p);
//            log.info("SUCCESS!!");
//        } catch (IOException error) {
//            log.error("slack 메시지 발송 중 문제가 발생.");
//        }
    }

}
