package com.example.Post.service;

import com.example.Post.dto.PostRequestDto;
import com.example.Post.dto.PostResponseDto;
import com.example.Post.entity.Post;
import com.example.Post.repository.PostRepository;
import com.mysql.cj.protocol.x.Notice;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    public List<PostResponseDto> getIdPost(Long id) {
        return postRepository.findById(id).stream().map(PostResponseDto::new).toList(); // 굳이 리스트에 담을 필요없음
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        Post savePost = postRepository.save(post);
        return new PostResponseDto(savePost);
    }

    @Transactional
    public Post updatePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);
        if(checkPassWord(post, requestDto.getPassword())){
            post.update(requestDto);
        }
        return  post;
    }


    public String deletePost(Long id, String password) {
        Post post = findPost(id);
        if(checkPassWord(post, password)){
            postRepository.delete(post);
            return "success";
        }
        return "fail";
    }
    private boolean checkPassWord(Post post, String getpassword){
        if(post.getPassword().equals(getpassword)){
            return true;
        }
        else{
            throw new IllegalArgumentException("패스워드 일치하지 않음.");
        }
    }


    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }

}