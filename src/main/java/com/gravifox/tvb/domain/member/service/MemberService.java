package com.gravifox.tvb.domain.member.service;

import com.gravifox.tvb.domain.member.dto.mypage.MyInfoResponse;
import com.gravifox.tvb.domain.member.dto.mypage.PasswordChangeRequest;

public interface MemberService {
    MyInfoResponse getMyInfo(Long userNo);

    void changePassword(Long userNo, PasswordChangeRequest request);
}
