package com.tvb.domain.member.dto.login;

import com.tvb.domain.member.dto.AuthDTO;
import lombok.Data;

@Data
public class LoginResponse implements AuthDTO {
    private String userId;
    private String accessToken;

    @Override
    public String extractUserID() {
        return this.userId;
    }
}
