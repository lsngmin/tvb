package com.gravifox.tvb.domain.member.controller;

import com.gravifox.tvb.domain.member.dto.mypage.MyInfoResponse;
import com.gravifox.tvb.domain.member.service.MemberService;
import com.gravifox.tvb.security.jwt.principal.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//
@Tag(
        name="회원 정보 조회",
        description = "인증된 사용자의 회원 정보를 반환합니다. 액세스 토큰 기반으로 사용자 번호를 추출하고, 그에 해당하는 정보를 조회합니다."
)
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(
                summary = "내 정보 조회",
            description = "로그인한 사용자의 회원 정보를 반환합니다. JWT 토큰 기반 인증이 필요하며, 반환 데이터에는 사용자 ID 등이 포함됩니다.",
            responses = {
                        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyInfoResponse.class))),
                        @ApiResponse(responseCode = "401", description = "인증 실패 - 유효하지 않은 토큰 또는 토큰 없음", content = @Content(mediaType = "application/josn"))
            }
    )

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userNo = Long.parseLong(userPrincipal.getName());

        MyInfoResponse myInfo = memberService.getMyInfo(userNo);

        return ResponseEntity.ok(myInfo);
    }
}
