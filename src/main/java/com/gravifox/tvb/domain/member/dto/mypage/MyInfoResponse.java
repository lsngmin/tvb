package com.gravifox.tvb.domain.member.dto.mypage;

import com.gravifox.tvb.domain.member.dto.AuthDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "내 정보 조회 응답 DTO")
@Getter
@Builder
public class MyInfoResponse implements AuthDTO {

    @Schema(description = "사용자 고유 번호", example = "1")
    private final Long userNo;

    @Schema(description = "사용자의 이메일", example = "user@example.com")
    private final String userId;

    @Schema(description = "사용자 닉네임", example = "홍길동")
    private final String nickname;

    @Schema(description = "사용자 가입일", example = "2024-01-01T12:34:56")
    private final LocalDateTime createdAt;

    @Schema(description = "로그인 유형 (예: LOCAL, KAKAO)", example = "LOCAL")
    private final String loginType;

    @Override
    public String extractUserID() {
        return this.userId;
    }
}
