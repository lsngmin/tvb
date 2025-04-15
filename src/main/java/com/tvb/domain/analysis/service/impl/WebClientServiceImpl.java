package com.tvb.domain.analysis.service.impl;

import com.tvb.domain.analysis.service.WebClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class WebClientServiceImpl implements WebClientService {
    @Value("${backend.upload.path}") private String uploadPath;
    @Value("${ai.base.url}") private String fastApiUrl;

    private WebClient webClient = WebClient.builder()
            .baseUrl(fastApiUrl)
            .build();

    @Override
    public String sendImageToAIServer(String uuid) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        try {
            InputStream is = Files.newInputStream(findFilePath(uuid));
            byte[] imageBytes = is.readAllBytes();  // Java 9 이상
            ByteArrayResource resource = new ByteArrayResource(imageBytes);
            bodyBuilder.part("file", resource)
                    .filename("detectedObject.jpg")
                    .contentType(MediaType.IMAGE_JPEG);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("{}", fastApiUrl);
        WebClient webClient = WebClient.builder()
                .baseUrl(fastApiUrl)
                .build();

        String analyzeResult = webClient.post()
                .uri("/upload/")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(bodyBuilder.build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("{}", analyzeResult);
        return analyzeResult;
    }
    private Path findFilePath(String uuid) {
        Path dirPath = Paths.get(uploadPath);
        try (Stream<Path> stream = Files.list(dirPath)) {
            Optional<Path> foundPath = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals(uuid))
                    .findFirst();
            Path filePath = null;
            if (foundPath.isPresent()) {
                filePath = foundPath.get();
                log.info("{}", filePath);
                return filePath;
            }
            //TODO Need Throw Exception
            log.info("Failed to find file path {}", dirPath);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Failed to find file path2 {}", dirPath);
        return null;

    }
}
