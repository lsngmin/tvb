package com.gravifox.tvb.domain.analysis.controller;

import com.gravifox.tvb.domain.analysis.service.WebClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/images")
public class AnalyzeImageController {
    private final String fastApiUrl = "http://localhost:58651";

    @Autowired
    private WebClientService webClientService;

    @PostMapping("/{uuid}/analyze")
    public ResponseEntity<?> analyzeImage(@PathVariable("uuid") String uuid) {
        log.info("AnalyzeImageController: analyzeImage: {}", uuid);
        String analyzeResult = webClientService.sendImageToAIServer(uuid);
        log.info("{}", analyzeResult);
        try {
            // 응답 지연 - 3초
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 처리
        }

        return ResponseEntity.ok().body(analyzeResult);
    }

    @PostMapping("/send_data")
    public String sendDataToFastAPI() throws IOException {
       return null;
    }
}
