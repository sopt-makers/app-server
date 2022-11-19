package org.sopt.app.presentation.user;

import lombok.RequiredArgsConstructor;
import org.sopt.app.application.user.UserUseCase;
import org.sopt.app.application.user.dto.SignUpUserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    /**
     * 이메일 회원가입
     */
    @PostMapping(value = "/api/v1/user/signup")
    public void signUp(@RequestBody SignUpUserDto request) {
        userUseCase.signUp(SignUpUserDto.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword())
                .osType(request.getOsType())
                .clientToken(request.getClientToken())
                .build());
    }
}
