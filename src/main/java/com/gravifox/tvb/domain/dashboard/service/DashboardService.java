package com.gravifox.tvb.domain.dashboard.service;

import com.gravifox.tvb.domain.dashboard.dto.DashboardInfoResponse;

public interface DashboardService {
    public DashboardInfoResponse getDashboardData(Long userNo);
    public String generateDashboardApiKey(Long userNo);
}
