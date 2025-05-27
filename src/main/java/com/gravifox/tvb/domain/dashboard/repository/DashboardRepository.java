package com.gravifox.tvb.domain.dashboard.repository;

import com.gravifox.tvb.domain.dashboard.domain.Dashboard;
import com.gravifox.tvb.domain.member.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    @Query("SELECT d.apiKey FROM Dashboard d WHERE d.user.userNo = :userNo")
    Optional<String> findDashboardByUser(@Param("userNo") Long userNo);

}
