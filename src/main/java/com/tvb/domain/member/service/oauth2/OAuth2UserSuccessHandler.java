package com.tvb.domain.member.service.oauth2;

import com.tvb.domain.member.dto.auth.AuthRequest;
import com.tvb.domain.member.domain.user.User;
import com.tvb.domain.member.repository.SocialLoginRepository;
import com.tvb.security.jwt.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final SocialLoginRepository socialLoginRepository;

    @Value("${front.redirect.url}") private String url;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("onAuthenticationSuccessayuthen: {}", authentication);
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("onAuthenticationSuccessoau2: {}", oAuth2User);
        String socialId = oAuth2User.getAttribute("email");
        log.info("OAuth2 authentication success. Social email={}", socialId);

        Optional<User> user = socialLoginRepository.findUserBySocialId(socialId);
        log.info("onAuthenticationSuccessuss: {}", user);
        if (user.isPresent()) {
            log.info("User found. userId={}, socialEmail={}", user.get().getUserId(), socialId);

            String refreshToken = jwtUtil.createToken(
                    AuthRequest.builder()
                            .user(user.get())
                            .build().getDataMap(),
                    600);
            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);//TODO: 현재는 https 통신을 지원하지 않아 비활성화 했지만 추후 https 통신 연결시 true 바꾸어야 한다.
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setAttribute("SameSite", "None");
            response.addCookie(cookie);
            log.info("Refresh token set in cookie for userId={}", user.get().getUserId());

        } else {
            System.out.println("NOOOOOOO");
        }
        log.info("url received: {}", url);
        response.sendRedirect(url);
    }
}
