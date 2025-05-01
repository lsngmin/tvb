package com.gravifox.tvb.domain.member.dto.mypage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "내 정보 조회 응답 DTO")
@Getter
@Builder
public class MyInfoResponse {

    @Schema(description = "사용자의 이메일 또는 이메일", example = "user@example.com", required = true)
    private String userId;
}
