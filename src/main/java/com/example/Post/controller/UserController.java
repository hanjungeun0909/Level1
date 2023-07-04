package com.example.Post.controller;

import com.example.Post.dto.StatusMsgResponse;
import com.example.Post.dto.userDto.LoginRequestDto;
import com.example.Post.dto.userDto.SignupRequestDto;
import com.example.Post.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<StatusMsgResponse> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok(new StatusMsgResponse(HttpStatus.OK.value(), "회원가입 성공"));
    }
    @PostMapping("/login")
    public ResponseEntity<StatusMsgResponse> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        userService.login(requestDto, res);
        return ResponseEntity.ok(new StatusMsgResponse(HttpStatus.OK.value(), "로그인 성공"));
    }
}
