package com.gravifox.tvb.domain.member.service.impl;

import com.gravifox.tvb.domain.member.domain.user.LoginType;
import com.gravifox.tvb.domain.member.dto.register.RegisterRequest;
import com.gravifox.tvb.domain.member.dto.register.RegisterResponse;
import com.gravifox.tvb.domain.member.domain.Password;
import com.gravifox.tvb.domain.member.domain.Profile;
import com.gravifox.tvb.domain.member.domain.user.User;
import com.gravifox.tvb.domain.member.exception.DataIntegrityViolationException;
import com.gravifox.tvb.domain.member.repository.ProfileRepository;
import com.gravifox.tvb.domain.member.repository.UserRepository;
import com.gravifox.tvb.domain.member.repository.PasswordRepository;
import com.gravifox.tvb.domain.member.service.RegisterService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired private UserRepository userRepository;
    @Autowired private ProfileRepository profileRepository;
    @Autowired private PasswordRepository passwordRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegisterResponse toRegisterUser(RegisterRequest registerRequestData) {
        RegisterRequest.UserRequestData userD_ = registerRequestData.getUser();
        RegisterRequest.ProfileRequestData profileD_ = registerRequestData.getProfile();
        RegisterRequest.PasswordRequestData passwordD_ = registerRequestData.getPassword();

        userRepository.findByUserId(userD_.getUserId()).ifPresent(user -> {
            throw DataIntegrityViolationException.forDuplicateUserId();
        });
        profileRepository.findByNickname(profileD_.getNickname()).ifPresent(profile -> {
            //todo 지금은 프론트엔드에서 닉네임 적는 곳이 없어 랜덤 값이 들어가지만 차후 닉네임이 설정되면 예외처리 개선 필요
            throw new DataIntegrityViolationException();
        });

        User user = User.builder()
                .userId(userD_.getUserId())
                .loginType(LoginType.fromString(userD_.getLoginType()))
                .build();

        userRepository.save(user);

        Profile profile = Profile.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .nickname(profileD_.getNickname())
                .user(user)
                .build();
       profileRepository.save(profile);

        Password password = Password.builder()
                .password(passwordEncoder.encode(passwordD_.getPassword()))
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();
        passwordRepository.save(password);
        return toRegisterResponse(user);
    }
    private RegisterResponse toRegisterResponse(User user) {
        return new RegisterResponse(user.getUserId());
    }
}
