package com.bamboo.BambooBomb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bamboo.BambooBomb.model.Post;
import com.bamboo.BambooBomb.service.PostService;
import com.bamboo.BambooBomb.dto.PostRequest;

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
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {
        Post createdPost = postService.createPost(postRequest.getTitle(), postRequest.getContent(), postRequest.getUserId());
        return ResponseEntity.ok(createdPost);
    }
    
}
