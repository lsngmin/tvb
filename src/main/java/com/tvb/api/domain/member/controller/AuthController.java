package com.tvb.api.domain.member.controller;

import com.tvb.api.domain.member.dto.AuthRequest;
import com.tvb.api.domain.member.entity.User;
import com.tvb.api.domain.member.exception.TokenNotFoundException;
import com.tvb.api.domain.member.service.AuthService;
import com.tvb.api.jwt.security.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response, @RequestBody AuthRequest authRequest) {
        log.info("Login request: {}", authRequest);
        return Optional.ofNullable(authService.makeTokenAndLogin(authRequest))
                .map(tokenResponse -> Pair.of(
                        authService.storeRefreshTokenInCookie(tokenResponse.get("refreshToken")),
                        tokenResponse.get("accessToken")
                ))
                .map(pair -> {
                    response.addCookie(pair.getFirst());
                    return ResponseEntity.ok(Map.of("accessToken", pair.getSecond()));
                })
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader(value = "Authorization", required = false) String accessToken,
                                     @CookieValue(name = "refreshToken") String refreshToken,
                                     HttpServletResponse response) {
        Map<String, String> map = authService.RefreshToken(accessToken, refreshToken);
        log.info("Refresh token: {}", map.get("refreshToken"));
        log.info("Access token: {}", map.get("accessToken"));
        response.addCookie(authService.storeRefreshTokenInCookie(map.get("refreshToken")));
        return ResponseEntity.ok(Map.of("accessToken", map.get("accessToken")));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String accessToken,
                                @CookieValue(name = "refreshToken") String refreshToken) {
        log.info("Access token: {}", accessToken);
        log.info("Refresh token: {}", refreshToken);


        if (accessToken == null || accessToken.isBlank()) {
            //** TODO: 액세스 토큰이 없으면 리프레시토큰을 검사하고 있으면 액세스 토큰을 발급해서 사용자 정보를 가져오고
            //** TODO: 리프레시 토큰이 없으면 사용자가 인증되지 않았으므로 사용자가 존재하지 않는다고 판단함
            throw new TokenNotFoundException();
        }
        //TODO: 액세스 토큰이 있으면 토큰의 유효성을 검사함
        return ResponseEntity.ok(authService.validateUserToken(accessToken.substring(7)));
    }
}
