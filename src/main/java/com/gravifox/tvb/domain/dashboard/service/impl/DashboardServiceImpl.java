package com.gravifox.tvb.domain.dashboard.service.impl;

import com.gravifox.tvb.domain.dashboard.dto.DashboardInfoResponse;
import com.gravifox.tvb.domain.dashboard.service.DashboardService;
import com.gravifox.tvb.security.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final JWTUtil jwtUtil;

    @Override
    public DashboardInfoResponse getDashboardData(Long userNo) {

        return null;
    }
}
