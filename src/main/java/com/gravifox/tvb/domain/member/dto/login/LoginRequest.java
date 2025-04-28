package com.gravifox.tvb.domain.member.dto.login;


import com.gravifox.tvb.domain.member.domain.Password;
import com.gravifox.tvb.domain.member.domain.user.User;
import com.gravifox.tvb.domain.member.dto.AuthDTO;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements AuthDTO {
    private User user;
    private Password password;

    public Map<String, String> getDataMap() {
        Map<String, String> data = new HashMap<>();
        data.put("userId", this.user.getUserId());
        data.put("userNo", String.valueOf(this.user.getUserNo()));
        return data;
    }


    public void changeUser(User user) {
        this.user = user;
    }

    @Override
    public String extractUserID() {
        return this.user.getUserId();
    }
}
