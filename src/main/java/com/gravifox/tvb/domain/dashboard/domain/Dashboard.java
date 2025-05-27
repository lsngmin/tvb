package com.gravifox.tvb.domain.dashboard.domain;

import com.gravifox.tvb.domain.member.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dashboard")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "did", nullable = false, unique = true, updatable = false)
    private Long did;

    @Column(name = "api_key")
    private String apiKey;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", referencedColumnName = "user_no", nullable = false)
    private User user;
}
