package com.tvb.domain.member.controller;

import com.tvb.domain.member.dto.register.RegisterRequestData;
import com.tvb.domain.member.dto.register.RegisterResponse;
import com.tvb.domain.member.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/register")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    /**
     * Handles user registration. This endpoint does not require JWT authentication.
     *
     * @param registerRequestData request payload containing user, profile, and password information
     * @return HTTP 200 with RegisterResponse on successful registration
     */
    @PostMapping
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequestData registerRequestData) {
        return ResponseEntity.ok(registerService.toRegisterUser(registerRequestData));
    }
}
