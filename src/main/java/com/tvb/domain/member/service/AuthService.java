package com.tvb.domain.member.service;

import com.tvb.domain.member.dto.auth.AuthRequest;
import jakarta.servlet.http.Cookie;

import java.util.Map;

public interface AuthService {
    Map<String, String> makeTokenAndLogin(AuthRequest authRequest);
    Map<String, String> RefreshToken(String accessToken, String refreshToken);
    Map<String, Object> validateUserToken(String accessToken);

    Cookie storeRefreshTokenInCookie(String cValue);
}
