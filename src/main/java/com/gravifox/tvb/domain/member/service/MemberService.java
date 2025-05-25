package com.gravifox.tvb.domain.member.service;

import com.gravifox.tvb.domain.member.dto.mypage.MyInfoResponse;

public interface MemberService {
    MyInfoResponse getMyInfo(Long userNo);
    void deleteAccount(Long userNo);
}
