package com.gravifox.tvb.domain.dashboard.controller;

import com.gravifox.tvb.domain.dashboard.dto.DashboardInfoResponse;
import com.gravifox.tvb.domain.dashboard.service.DashboardService;
import com.gravifox.tvb.security.jwt.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    @Autowired
    private final DashboardService dashboardService;

    @GetMapping("/")
    public ResponseEntity<?> index(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userNo = Long.parseLong(userPrincipal.getName());
        DashboardInfoResponse d = dashboardService.getDashboardData(userNo);
        return ResponseEntity.ok().body(d);
    }
}
