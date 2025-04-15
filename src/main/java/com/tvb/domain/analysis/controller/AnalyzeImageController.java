package com.tvb.domain.analysis.controller;

import com.tvb.domain.analysis.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/images")
public class AnalyzeImageController {
    private final String fastApiUrl = "http://localhost:58651";

    @Autowired
    private WebClientService webClientService;

    @PostMapping("/{uuid}/analyze")
    public ResponseEntity<?> analyzeImage(@PathVariable String uuid) {
        String uuuid = "d443616d-90cb-4114-81af-4c4495c06349_Pic.jpg";
        String analyzeResult = webClientService.sendImageToAIServer(uuuid);
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
