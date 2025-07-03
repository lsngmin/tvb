package com.gravifox.tvb.domain.admin.repository;

import com.gravifox.tvb.domain.admin.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface TermRepository extends JpaRepository<Term, Long> {
    @Query("SELECT t.termId, t.termVersion FROM Term t WHERE t.termTitle = :termTitle")
    Map<String, ?> getTermIdAndAgreedVersionByTermTitle(String termTitle);
}
