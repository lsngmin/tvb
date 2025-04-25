package com.tvb.domain.member.dto.register.module;

import com.tvb.domain.member.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterProfileRequestData {
    @NotNull private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
}
