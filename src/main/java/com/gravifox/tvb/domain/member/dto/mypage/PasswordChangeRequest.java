package com.gravifox.tvb.domain.member.dto.mypage;

public record PasswordChangeRequest(
        String currentPassword,
        String newPassword
) {}
