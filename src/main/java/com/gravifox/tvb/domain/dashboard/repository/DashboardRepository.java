package com.gravifox.tvb.domain.dashboard.repository;

import com.gravifox.tvb.domain.dashboard.domain.Dashboard;
import com.gravifox.tvb.domain.member.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    @Query("SELECT d.apiKey FROM Dashboard d WHERE d.user.userNo = :userNo")
    Optional<String> findApiKeyByUser(@Param("userNo") Long userNo);

    @Query("SELECT d FROM Dashboard d WHERE d.user.userNo = :userNo")
    Optional<Dashboard> findDashboardByUserNo(@Param("userNo") Long userNo);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Dashboard d SET d.apiKey = :apiKey WHERE d.user.userNo = :userNo")
    void updateApiKey(@Param("userNo") Long userNo,
                      @Param("apiKey") String apiKey);
}
