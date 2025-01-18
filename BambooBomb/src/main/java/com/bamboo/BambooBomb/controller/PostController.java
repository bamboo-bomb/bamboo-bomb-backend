package com.bamboo.BambooBomb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bamboo.BambooBomb.model.Post;
import com.bamboo.BambooBomb.service.PostService;
import com.bamboo.BambooBomb.dto.PostRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        // 제목과 내용의 길이 제한 검증
        if (postRequest.getTitle().length() > 20) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title must not exceed 20 characters.");
        }
        if (postRequest.getContent().length() > 1000) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content must not exceed 1000 characters.");
        }

        Post createdPost = postService.createPost(postRequest.getTitle(), postRequest.getContent(), postRequest.getUserId());
        return ResponseEntity.ok(createdPost);
    }
    
}
