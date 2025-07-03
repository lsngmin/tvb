package com.gravifox.tvb.domain.member.repository;

import com.gravifox.tvb.domain.member.domain.UserTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTermRepository extends JpaRepository<UserTerm, Long> {
}
