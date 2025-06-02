package com.gravifox.tvb.api.domain.dashboard.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gravifox.tvb.domain.dashboard.controller.DashboardController;
import com.gravifox.tvb.domain.dashboard.domain.Dashboard;
import com.gravifox.tvb.domain.dashboard.dto.DashboardInfoResponse;
import com.gravifox.tvb.domain.dashboard.repository.DashboardRepository;
import com.gravifox.tvb.domain.dashboard.service.DashboardService;
import com.gravifox.tvb.domain.member.domain.user.LoginType;
import com.gravifox.tvb.domain.member.domain.user.User;
import com.gravifox.tvb.domain.member.repository.UserRepository;
import com.gravifox.tvb.security.jwt.principal.UserPrincipal;
import com.gravifox.tvb.security.jwt.util.JWTUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DashboardControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private DashboardRepository dashboardRepo;

    private User savedUser;
    private Dashboard savedDash;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DashboardRepository dashboardRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .loginType(LoginType.EMAIL)
                .userId("test123@example.com")
                .build();
        savedUser = userRepository.save(user);

        Dashboard dash = Dashboard.builder()
                .user(savedUser)
                .apiKey("abc-123-key")
                .build();
        savedDash = dashboardRepository.save(dash);
    }

    @Test
    @DisplayName("인증된 JWT 사용자로 요청하면 200, apiKey 반환")
    void getDashboard_withRealSecurityContext() throws Exception {
        String token = jwtUtil.createToken(Map.of("userId", "test123@example.com",
                "userNo", String.valueOf(savedUser.getUserNo())), 10);

        mockMvc.perform(get("/api/v1/dashboard/")
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiKey").doesNotExist());

    }
    @Test
    @DisplayName("토큰 발급 요청이 오면 토큰 반환 및 200 반환")
    void getDashboard_withJWT() throws Exception {
        String token = jwtUtil.createToken(Map.of("userId", "test@example.com",
                "userNo", String.valueOf(savedUser.getUserNo())), 10);



        mockMvc.perform(post("/api/v1/dashboard/generate")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiKey").doesNotExist());
    }
}
