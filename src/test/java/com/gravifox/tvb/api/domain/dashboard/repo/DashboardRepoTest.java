package com.gravifox.tvb.api.domain.dashboard.repo;

import com.gravifox.tvb.domain.dashboard.domain.Dashboard;
import com.gravifox.tvb.domain.dashboard.repository.DashboardRepository;
import com.gravifox.tvb.domain.member.domain.user.LoginType;
import com.gravifox.tvb.domain.member.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DashboardRepoTest {
    @Autowired
    DashboardRepository dashboardRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void findByUserNo_returnsDashboard() {
        User u = new User().builder()
                .userId("TEST!@#123")
                .loginType(LoginType.EMAIL)
                .build();
        entityManager.persist(u);

        Dashboard d = new Dashboard().builder()
                .apiKey("API키 입니다.")
                .user(u)
                .build();
        entityManager.persist(d);

        entityManager.flush();

        Optional<String> foundApiKey = dashboardRepository.findDashboardByUser(u.getUserNo());
        assertThat(foundApiKey).isPresent();
    }

}
