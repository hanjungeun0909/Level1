package com.example.week1test1.dto;

import com.example.week1test1.entity.Notice;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String author;
//    private String password; 노출되면 안되기 때문에
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public NoticeResponseDto(Notice notice){
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.author = notice.getAuthor();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.modifiedAt = notice.getModifiedAt();
    }

    public NoticeResponseDto(Long id, String title, String author, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
