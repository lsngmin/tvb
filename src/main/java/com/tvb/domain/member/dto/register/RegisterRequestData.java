package com.tvb.domain.member.dto.register;

import com.tvb.domain.member.dto.register.module.RegisterPasswordRequestData;
import com.tvb.domain.member.dto.register.module.RegisterProfileRequestData;
import com.tvb.domain.member.dto.register.module.RegisterUserRequestData;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestData {
    private RegisterUserRequestData user;
    private RegisterProfileRequestData profile;
    private RegisterPasswordRequestData password;
}
