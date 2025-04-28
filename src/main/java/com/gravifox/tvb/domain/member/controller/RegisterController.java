package com.gravifox.tvb.domain.member.controller;

import com.gravifox.tvb.annotation.LogContext;
import com.gravifox.tvb.domain.member.dto.register.RegisterRequest;
import com.gravifox.tvb.domain.member.dto.register.RegisterResponse;
import com.gravifox.tvb.domain.member.exception.register.InvalidFormatException;
import com.gravifox.tvb.domain.member.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;
import java.util.function.Predicate;

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
    @LogContext(action = "UserRegistration", detail="UserId")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequestData) {
        //RequestData의 유효성 검증 로직입니다.
        validateRequestData.accept(registerRequestData);
        return ResponseEntity.ok(registerService.toRegisterUser(registerRequestData));
    }

    Predicate<RegisterRequest.PasswordRequestData> passswordValidator = pwd ->
            pwd.getPassword().matches("^(?=.*[A-Za-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:\";'<>?,./]).{8,20}$");
    Predicate<RegisterRequest.UserRequestData> userIdValidator = user ->
        !user.getUserId().isBlank() &&
                user.getUserId().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    Predicate<RegisterRequest.ProfileRequestData> profileValidator = profile ->
            !profile.getNickname().isBlank();
    Predicate<RegisterRequest.UserRequestData> loginTypeValidator = user ->
            !user.getLoginType().isBlank();


    Consumer<RegisterRequest> validateRequestData = r -> {
        if(!userIdValidator.test(r.getUser())) {
            throw InvalidFormatException.forInvalidUserId(r.getUser().getUserId());
        }
        if(!passswordValidator.test(r.getPassword())) {
            throw InvalidFormatException.forInvalidPassword(r.getPassword().getPassword());
        }
        if (!profileValidator.test(r.getProfile())) {
            throw InvalidFormatException.forInvalidNickName(r.getProfile().getNickname());
        }
        if (!loginTypeValidator.test(r.getUser())) {
            throw InvalidFormatException.forInvalidLoginType(r.getUser().getLoginType());
        }
    };

}
