package com.tvb.domain.member.service;

import com.tvb.domain.member.dto.login.LoginRequest;
import jakarta.servlet.http.Cookie;

import java.util.Map;

public interface AuthService {
    Map<String, String> makeTokenAndLogin(LoginRequest loginRequest);
    Map<String, String> RefreshToken(String accessToken, String refreshToken);
    Map<String, Object> validateUserToken(String accessToken);

    Cookie storeRefreshTokenInCookie(String cValue);
}
