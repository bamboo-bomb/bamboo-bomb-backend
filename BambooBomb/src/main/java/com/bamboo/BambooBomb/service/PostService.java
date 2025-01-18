package com.bamboo.BambooBomb.service;

import org.springframework.stereotype.Service;

import com.bamboo.BambooBomb.model.Post;
import com.bamboo.BambooBomb.repository.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(String title, String content, String userId) {
        Post newPost = new Post(title, content, userId);
        return postRepository.save(newPost);
    }
}
