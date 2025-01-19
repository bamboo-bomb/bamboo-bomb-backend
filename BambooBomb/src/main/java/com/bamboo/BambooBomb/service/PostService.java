package com.bamboo.BambooBomb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 포스트 조회(페이징 처리)
    public Page<Post> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }

    // 특정 포스트 조회(id)
    public Post findPostById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    // 포스트 삭제
    public boolean deletePost(String id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
