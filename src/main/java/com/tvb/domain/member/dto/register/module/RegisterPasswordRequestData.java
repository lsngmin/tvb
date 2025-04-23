package com.tvb.domain.member.dto.register.module;

import com.tvb.domain.member.domain.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPasswordRequestData {
    @NotNull(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$",
            message = "Password must be 8–20 characters long and include letters, numbers, and special characters"
    )
    private String password;
    private LocalDateTime updatedAt;
    private User user;
}
