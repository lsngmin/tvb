package com.tvb.domain.member.repository;

import com.tvb.domain.member.domain.SocialLogin;
import com.tvb.domain.member.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {
    Optional<SocialLogin> findBySocialId(String socialId);

    @Query("SELECT s.user FROM SocialLogin s WHERE s.socialId = :socialId")
    Optional<User> findUserBySocialId(String socialId);
}
