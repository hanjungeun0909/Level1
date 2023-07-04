package com.example.Post.controller;

import com.example.Post.dto.PostRequestDto;
import com.example.Post.dto.PostResponseDto;
import com.example.Post.dto.StatusMsgResponse;
import com.example.Post.entity.Post;
import com.example.Post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }
    @GetMapping("/post/{id}")
    public PostResponseDto getIdPost(@PathVariable Long id) {
        return postService.getIdPost(id);
    }
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest res) {
        return postService.createPost(requestDto, res);
    }
    @PutMapping("/post/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest res){
        return postService.updatePost(id, requestDto, res);
    }
    @DeleteMapping("/post/{id}")
    public ResponseEntity<StatusMsgResponse> deletePost(@PathVariable Long id, HttpServletRequest res){
        postService.deletePost(id, res);
        return ResponseEntity.ok(new StatusMsgResponse(HttpStatus.OK.value(),"삭제 성공!"));
    }
}
