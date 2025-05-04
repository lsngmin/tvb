package com.gravifox.tvb.domain.member.dto.login;


import com.gravifox.tvb.domain.member.domain.Password;
import com.gravifox.tvb.domain.member.domain.user.User;
import com.gravifox.tvb.domain.member.dto.AuthDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Schema(description = "사용자 로그인 요청 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements AuthDTO {

    @Schema(description = "로그인에 사용할 사용자 정보",
            required = true,
            example = """
        {
            "userId": "user@example.com",
            "userNo": 1
        }
        """
    )
    private User user;

    @Schema(description = "사용자 비밀번호 정보",
            required = true,
            example = """
        {
            "password": "encrypted_password"
        }
        """
    )
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
