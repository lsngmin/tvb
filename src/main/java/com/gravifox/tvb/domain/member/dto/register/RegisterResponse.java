package com.gravifox.tvb.domain.member.dto.register;

import com.gravifox.tvb.domain.member.dto.AuthDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterResponse implements AuthDTO {
    @Override
    public String extractUserID() {
        return this.userId;
    }

    private String userId;
}
