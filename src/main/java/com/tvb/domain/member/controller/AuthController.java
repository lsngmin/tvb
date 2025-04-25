package com.tvb.domain.member.controller;

import com.tvb.domain.member.dto.auth.AuthRequest;
import com.tvb.domain.member.service.AuthService;
import com.tvb.infra.logging.slack.service.SLog;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final SLog sLog;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response, @RequestBody AuthRequest authRequest) {
        log.info("Login request: {}", authRequest);
        sLog.info("로그인 서비스 로직");

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
                                     @CookieValue(name = "refreshToken", required = false) String refreshToken,
                                     HttpServletResponse response) {
        if(refreshToken == null) {
            log.info("Not Found Refresh Token or Unauthorized User");
            return ResponseEntity.ok().build();
        }
        Map<String, String> map = authService.RefreshToken(accessToken, refreshToken);
        log.info("Refresh token: {}", map.get("refreshToken"));
        log.info("Access token: {}", map.get("accessToken"));
        response.addCookie(authService.storeRefreshTokenInCookie(map.get("refreshToken")));
        return ResponseEntity.ok(Map.of("accessToken", map.get("accessToken")));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String accessToken,
                                @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        log.info("Access token: {}", accessToken);
        log.info("Refresh token: {}", refreshToken);


        if (accessToken == null || accessToken.isBlank()) {
            //** TODO: 액세스 토큰이 없으면 리프레시토큰을 검사하고 있으면 액세스 토큰을 발급해서 사용자 정보를 가져오고
            //** TODO: 리프레시 토큰이 없으면 사용자가 인증되지 않았으므로 사용자가 존재하지 않는다고 판단함
            return ResponseEntity.ok().build();
        }
        //TODO: 액세스 토큰이 있으면 토큰의 유효성을 검사함
        return ResponseEntity.ok(authService.validateUserToken(accessToken.substring(7)));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String accessToken,

                                    HttpServletResponse response) {
        log.info("Logout Request: {}", response.getStatus());
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
