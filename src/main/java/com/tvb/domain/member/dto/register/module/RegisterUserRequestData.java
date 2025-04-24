package com.tvb.domain.member.dto.register.module;

import com.tvb.domain.member.domain.LoginType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestData {
    private String userId;
    LoginType loginType;
}
