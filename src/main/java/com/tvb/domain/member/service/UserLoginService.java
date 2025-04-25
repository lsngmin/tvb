package com.tvb.domain.member.service;

import com.tvb.domain.member.dto.auth.AuthRequest;

import java.util.Optional;

public interface UserLoginService {
    public Optional<AuthRequest> login_fromGoogle();
    public Optional<AuthRequest> login_fromFacebook();
    public Optional<AuthRequest> login_fromTwitter();
    public Optional<AuthRequest> login_fromNaver();
    public Optional<AuthRequest> login_fromKakao();

    public Optional<AuthRequest> login_fromEmail();
}
