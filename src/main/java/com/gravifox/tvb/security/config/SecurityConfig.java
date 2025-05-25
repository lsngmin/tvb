package com.gravifox.tvb.security.config;

import com.gravifox.tvb.security.jwt.filter.JWTCheckFilter;
import com.gravifox.tvb.domain.member.service.oauth2.OAuth2UserService;
import com.gravifox.tvb.domain.member.service.oauth2.OAuth2UserSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private JWTCheckFilter jwtCheckFilter;
    private final OAuth2UserService OAuth2UserService;
    private final OAuth2UserSuccessHandler oAuth2UserSuccessHandler;

    @Value("${front.redirect.login-url}") private String loginUrl;

    @Autowired
    private void setJwtCheckFilter(JWTCheckFilter jwtCheckFilter) {this.jwtCheckFilter = jwtCheckFilter;}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))

//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html",
//                                "/docs/**",
//                                "/api/upload-swagger"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )

                .addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .oauth2Login(oauth -> oauth
                        .loginPage(loginUrl)
                        .userInfoEndpoint(userInfo -> userInfo.userService(OAuth2UserService))
                        .successHandler(oAuth2UserSuccessHandler)
                );

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type","X-Request-ID"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
