package com.example.Post.service;

import com.example.Post.dto.PostRequestDto;
import com.example.Post.dto.PostResponseDto;
import com.example.Post.dto.StatusMsgResponse;
import com.example.Post.entity.Post;
import com.example.Post.jwt.JwtUtil;
import com.example.Post.repository.PostRepository;
import com.example.Post.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

//    @Transactional(readOnly = true) // 오직 읽어오기만
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

//    @Transactional(readOnly = true)
    public PostResponseDto getIdPost(Long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다.")));
    }

    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest res) {

        //토큰가져오기
        String token = jwtUtil.getTokenFromRequest(res);
        System.out.println("token= "+ token);
        String substringToken = jwtUtil.substringToken(token);
        //검증
        boolean isValidateToken = jwtUtil.validateToken(substringToken);

        if (isValidateToken) {
            Post post = new Post(requestDto);
            Post savedPost = postRepository.save(post);
            return new PostResponseDto(savedPost);
        }else{
            throw new IllegalArgumentException("잘못된 접근입니다.C");
        }
    }

    @Transactional
    public Post updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request){
        Post post = findPost(id);

        // HttpServlet에서 쿠키 가져와 JWT 토큰 꺼내
        String token = jwtUtil.getTokenFromRequest(request);
        System.out.println("token= "+ token);
        String substringToken = jwtUtil.substringToken(token);
        String username = jwtUtil.getUserInfoFromToken(jwt).getSubject();

        if(jwtUtil.validateToken(jwt) && username.equals(post.getUsername())){
            post.update(requestDto);
        }else{
            throw new IllegalArgumentException("잘못된 접근입니다.U");

        }
        return post;
    }


    public void deletePost(Long id, HttpServletRequest res) {
        Post post = findPost(id);
        String username = "";
        // HttpServlet에서 쿠키 가져와 JWT 토큰 꺼내
        String jwt = jwtUtil.getTokenFromRequest(res);

        // 쿠키에서 JWT 토큰 자르기
        jwt = jwtUtil.substringToken(jwt);

        username = jwtUtil.substringToken(jwt);

        if(jwtUtil.validateToken(jwt) && username.equals(post.getUsername())){
            postRepository.delete(post);
        }else{
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }

//    private String getUsernameFromJwt(String tokenValue) {
//        // JWT 토큰 substring
//        String token = jwtUtil.substringToken(tokenValue);
//
//        // 토큰 검증
//        if(!jwtUtil.validateToken(token)){
//            throw new IllegalArgumentException("Token Error");
//        }
//
//        // 토큰에서 사용자 정보 가져오기
//        Claims info = jwtUtil.getUserInfoFromToken(token);
//        // 사용자 username
//        String username = info.getSubject();
//        System.out.println("username = " + username);
//
//        return username;
//    }

}