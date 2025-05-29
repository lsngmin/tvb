package com.gravifox.tvb.domain.dashboard.service.impl;

import com.gravifox.tvb.domain.dashboard.dto.DashboardInfoResponse;
import com.gravifox.tvb.domain.dashboard.repository.DashboardRepository;
import com.gravifox.tvb.domain.dashboard.service.DashboardService;
import com.gravifox.tvb.security.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final DashboardRepository dashboardRepository;
    private final JWTUtil jwtUtil;

    @Override
    public DashboardInfoResponse getDashboardData(Long userNo) {
        Optional<String> apiKey = dashboardRepository.findDashboardByUser(userNo);

        return apiKey
                .map(key -> DashboardInfoResponse.builder().apiKey(key).build())
                .orElseThrow(() -> new NoSuchElementException("Dashboard not found"));
    }

}

