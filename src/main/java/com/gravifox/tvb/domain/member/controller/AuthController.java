package com.gravifox.tvb.domain.member.controller;

import com.gravifox.tvb.annotation.LogContext;
import com.gravifox.tvb.domain.member.dto.login.LoginRequest;
import com.gravifox.tvb.domain.member.dto.login.LoginResponse;
import com.gravifox.tvb.domain.member.exception.common.ErrorMessageMap;
import com.gravifox.tvb.domain.member.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "인증", description = "로그인, 토큰 갱신, 사용자 정보 확인 및 로그아웃 기능을 제공합니다.")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "로그인",
            description = "로그인 요청을 수행하고, 요청이 성공하면 Access Token과 Refresh Token을 발급받습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공",content = @Content(schema = @Schema(implementation = LoginResponse.class))),
                    @ApiResponse(responseCode = "401", description = "인증 실패 - 잘못된 사용자 정보", content = @Content(mediaType = "application/json") ),

            }
    )

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

    @Operation(
            summary = "Access Token 갱신",
            description = "Refresh Token을 사용하여 Access Token을 재발급합니다. Access Token이 만료된 경우에만 호출됩니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "유효하지 않거나 만료된 토큰", content = @Content(mediaType ="application/json"))
            }
    )

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

    @Operation(
            summary = "현재 사용자 정보 확인",
            description = "Access Token을 통해 로그인된 사용자의 정보를 확인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "인증 실패 - 토큰 없음 / 유효하지 않음", content = @Content(mediaType = "application/json"))
            }
    )
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


        @Operation(
                summary = "사용자 로그아웃",
                description = "Refresh Token 쿠키를 만료시켜 로그아웃 처리를 합니다.",
                responses = {
                        @ApiResponse(responseCode = "200",description = "로그아웃 성공", content = @Content(mediaType = "application/json"))
                }
        )
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
