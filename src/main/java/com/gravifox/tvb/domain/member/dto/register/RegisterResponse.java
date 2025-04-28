package com.gravifox.tvb.domain.member.dto.register;

import com.gravifox.tvb.domain.member.dto.AuthDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterResponse implements AuthDTO {
    @Override
    public String extractUserID() {
        return this.userId;
    }

    @Schema(description = "사용자 아이디", example = "user@example.com")
    private String userId;
}
