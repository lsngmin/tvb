package com.gravifox.tvb.domain.member.controller;

import com.gravifox.tvb.annotation.LogContext;
import com.gravifox.tvb.domain.member.dto.login.LoginRequest;
import com.gravifox.tvb.domain.member.dto.login.LoginResponse;
import com.gravifox.tvb.domain.member.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @LogContext(action = "UserAuthentication", detail="UserId")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Map<String, String> token = authService.makeTokenAndLogin(loginRequest);
        response.addCookie(authService.storeRefreshTokenInCookie(token.get("refreshToken")));
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token.get("accessToken"));
        loginResponse.setUserId(token.get("userId"));
        return ResponseEntity.ok(loginResponse);

//        return Optional.ofNullable(authService.makeTokenAndLogin(loginRequest))
//                .map(tokenResponse -> Pair.of(
//                        authService.storeRefreshTokenInCookie(tokenResponse.get("refreshToken")),
//                        tokenResponse.get("accessToken")
//                ))
//                .map(pair -> {
//                    response.addCookie(pair.getFirst());
//                    return ResponseEntity.ok(Map.of("accessToken", pair.getSecond()));
//                })
//                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    /**
     *
     * 왜 토큰 파라미터의 required 가 false인가요 ? - true로 설정할 경우 토큰이 없으면 400 Bad Request를 반환하게 됩니다.
     *  하지만 이 경우에도 요청을 수용하여 200 OK를 반환함으로 UX를 저해하지 않고 부드럽게 처리할 수 있습니다.
     *
     * @param accessToken
     * @param refreshToken
     * @param response
     * @return
     */
    @PostMapping("/refresh")
    @LogContext(action = "UserTokenRefresh", detail="ONLY REFRESH NOT AUTHENTICATION!")
    public ResponseEntity<?> refresh(@RequestHeader(value = "Authorization", required = false) String accessToken,
                                     @CookieValue(name = "refreshToken", required = false) String refreshToken,
                                     HttpServletResponse response) {
        if(refreshToken == null) {
            return ResponseEntity.ok().build();
        }
        Map<String, String> map = authService.RefreshToken(accessToken, refreshToken);
        response.addCookie(authService.storeRefreshTokenInCookie(map.get("refreshToken")));
        return ResponseEntity.ok(Map.of("accessToken", map.get("accessToken")));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String accessToken,
                                @CookieValue(name = "refreshToken", required = false) String refreshToken) {
//        log.info("Access token: {}", accessToken);
//        log.info("Refresh token: {}", refreshToken);


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
