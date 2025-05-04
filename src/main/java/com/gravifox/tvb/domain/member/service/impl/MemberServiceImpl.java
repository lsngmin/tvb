package com.gravifox.tvb.domain.member.service.impl;

import com.gravifox.tvb.domain.member.dto.mypage.MyInfoResponse;
import com.gravifox.tvb.domain.member.repository.UserRepository;
import com.gravifox.tvb.domain.member.service.MemberService;
import com.gravifox.tvb.domain.member.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final UserRepository userRepository;

    @Override
    public MyInfoResponse getMyInfo(Long userNo) {
        return userRepository.findById(userNo)
                .map(user -> MyInfoResponse.builder()
                        .userId(user.getUserId())
                        .loginType(user.getLoginType().name())
                        .nickname(user.getProfile().getNickname())
                        .createdAt(user.getProfile().getCreatedAt())
                        .build())
                .orElseThrow(() -> new UserNotFoundException(userNo));
    }
}
