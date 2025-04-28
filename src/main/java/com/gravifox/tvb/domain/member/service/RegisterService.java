package com.gravifox.tvb.domain.member.service;

import com.gravifox.tvb.domain.member.dto.register.RegisterRequest;
import com.gravifox.tvb.domain.member.dto.register.RegisterResponse;

public interface RegisterService {
    RegisterResponse toRegisterUser(RegisterRequest registerRequestData);
}
