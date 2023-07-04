package com.example.Post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 인 값 생략
public class StatusMsgResponse {
    private int statusCode; // 200(OK) or 400(Bad Request)
    private String msg; // "회원가입 성공" or "회원가입 실패" or "게시글 삭제 성공"

    public StatusMsgResponse(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
