package com.tvb.domain.member.dto.register;

import com.tvb.domain.member.domain.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestData {
    @Valid @NotNull private UserRequestData user;
    @Valid @NotNull private ProfileRequestData profile;
    @Valid @NotNull private PasswordRequestData password;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRequestData {
        @NotNull private String userId;
        @NotNull String loginType;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileRequestData {
        @NotNull private String nickname;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private User user;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PasswordRequestData {
        @NotNull private String password;
        private LocalDateTime updatedAt;
        private User user;
    }
}
