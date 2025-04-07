package com.tvb.domain.analysis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/images")
public class AnalyzeImageController {

    @PostMapping("/{uuid}/analyze")
    public ResponseEntity<?> analyzeImage(@PathVariable String uuid) {
        log.info("{}", uuid);
        try {
            // 응답 지연 - 3초
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 처리
        }

        return ResponseEntity.ok().build();
    }
}
