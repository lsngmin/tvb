package com.gravifox.tvb.domain.member.controller;

import com.gravifox.tvb.domain.member.dto.mypage.MyInfoResponse;
import com.gravifox.tvb.security.jwt.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userNo = Long.parseLong(userPrincipal.getName());

        MyInfoResponse myInfo = memberService.getMyInfo(userNo);

        return ResponseEntity.ok(myInfo);
    }
}
