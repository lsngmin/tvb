package com.tvb.domain.member.service.impl;

import com.tvb.domain.member.dto.register.RegisterRequestData;
import com.tvb.domain.member.dto.register.RegisterResponse;
import com.tvb.domain.member.dto.register.module.RegisterPasswordRequestData;
import com.tvb.domain.member.dto.register.module.RegisterProfileRequestData;
import com.tvb.domain.member.dto.register.module.RegisterUserRequestData;
import com.tvb.domain.member.domain.Password;
import com.tvb.domain.member.domain.Profile;
import com.tvb.domain.member.domain.User;
import com.tvb.domain.member.exception.DataIntegrityViolationException;
import com.tvb.domain.member.repository.ProfileRepository;
import com.tvb.domain.member.repository.UserRepository;
import com.tvb.domain.member.repository.PasswordRepository;
import com.tvb.domain.member.service.RegisterService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired private UserRepository userRepository;
    @Autowired private ProfileRepository profileRepository;
    @Autowired private PasswordRepository passwordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegisterResponse toRegisterUser(RegisterRequestData registerRequestData) {
        RegisterUserRequestData userD_ = registerRequestData.getUser();
        RegisterProfileRequestData profileD_ = registerRequestData.getProfile();
        RegisterPasswordRequestData passwordD_ = registerRequestData.getPassword();

        userRepository.findByUserId(userD_.getUserId()).ifPresent(user -> {
            throw new DataIntegrityViolationException();
        });
        profileRepository.findByNickname(profileD_.getNickname()).ifPresent(profile -> {
            throw new DataIntegrityViolationException();
        });

        User user = User.builder()
                .userId(userD_.getUserId())
                .loginType(userD_.getLoginType())
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
