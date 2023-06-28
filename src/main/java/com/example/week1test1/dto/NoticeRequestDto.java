package com.example.week1test1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRequestDto {
    private String title; // 제목
    private String author; // 작성자
    private String content; // 작성내용
    private String password; // 비밀번호
//    @Getter
//    public static class PasswordDto{
//        private String password;
//    }

}
