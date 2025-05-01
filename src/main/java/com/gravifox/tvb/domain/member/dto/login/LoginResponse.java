package com.gravifox.tvb.domain.member.dto.login;

import com.gravifox.tvb.domain.member.dto.AuthDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "사용자 로그인 응답 DTO")
@Data
public class LoginResponse implements AuthDTO {

    @Schema(description = "사용자 이메일 또는 아이디", example = "user@example.com", required = true)
    private String userId;

    @Schema(description = "로그인 성공 시 발급되는 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", required = true)
    private String accessToken;

    @Override
    public String extractUserID() {
        return this.userId;
    }
}
