package com.tvb.domain.member.dto.auth;


import com.tvb.domain.member.domain.Password;
import com.tvb.domain.member.domain.User;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
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
}
