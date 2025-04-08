//package com.tvb.domain.analysis.service.impl;
//
//import com.tvb.domain.analysis.service.WebClientService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.MultipartBodyBuilder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//@Service
//public class WebClientServiceImpl implements WebClientService {
//    @Value("${backend.upload.path}") private String uploadPath;
//    @Value("${ai.base.url}") private String fastApiUrl;
//
//    private WebClient webClient = WebClient.builder()
//            .baseUrl(fastApiUrl)
//            .build();
//
//    @Override
//    public String sendImageToAIServer(String uuid) {
//        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
//        try {
//            InputStream is = Files.newInputStream(findFilePath(uuid));
//            bodyBuilder.part("image", is)
//                    .filename("detectedObject.jpg")
//                    .contentType(MediaType.IMAGE_JPEG);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String analyzeResult = webClient.post()
//                .uri("/upload/")
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .bodyValue(bodyBuilder.build())
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//        return analyzeResult;
//    }
//    private Path findFilePath(String uuid) {
//        Path dirPath = Paths.get(uploadPath);
//        try (Stream<Path> stream = Files.list(dirPath)) {
//            Optional<Path> foundPath = stream
//                    .filter(Files::isRegularFile)
//                    .filter(path -> path.getFileName().toString().equals(uuid))
//                    .findFirst();
//            if (foundPath.isPresent()) {
//                Path filePath = foundPath.get();
//                return filePath;
//            }
//            //TODO Need Throw Exception
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//}
