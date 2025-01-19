package com.bamboo.BambooBomb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bamboo.BambooBomb.model.Post;
import com.bamboo.BambooBomb.model.ReactionType;
import com.bamboo.BambooBomb.service.PostService;
import com.bamboo.BambooBomb.dto.PostRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 포스트 조회수 증가
    @GetMapping("/viewCount/{id}")
    public ResponseEntity<Post> incremnetViewCount(
        @PathVariable String id
        ) {
            Post post = postService.incrementViewCount(id);
            return ResponseEntity.ok(post);
    }

    

    // 포스트 조회(페이징 기본 - 페이지: 0, 사이즈: 10)
    @GetMapping("/getPostsPage")
    public ResponseEntity<Page<Post>> getPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Post> posts = postService.getPosts(page, size);
        return ResponseEntity.ok(posts);
    }


    // 특정 포스트 조회(id)
    @GetMapping("/{id}")
    public ResponseEntity<?> findPostById(@PathVariable String id) {
        Post post = postService.findPostById(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }
        return ResponseEntity.ok(post);
    }
    
    // 특정 포스트 조회 (유저 id)
    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<Post>> getPostsByAuthorId(
        @PathVariable String authorId,
        @RequestParam int page,
        @RequestParam int size) {
        
        Page<Post> posts = postService.getPostsByAuthorId(authorId, page, size);
        return ResponseEntity.ok(posts);
    }

    // 포스트 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id) {
        boolean deleted = postService.deletePost(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }
    }

    // 이모지 추가
    @PostMapping("/{postId}/reaction")
    public ResponseEntity<Post> addReaction(
        @PathVariable String postId,
        @RequestParam String userId,
        @RequestParam ReactionType reactionType) {
        
        Post updatedPost = postService.addReaction(postId, userId, reactionType);
        return ResponseEntity.ok(updatedPost);
    }

    // 이모지 삭제
    @DeleteMapping("/{postId}/reaction")
    public ResponseEntity<Post> removeReaction(
        @PathVariable String postId,
        @RequestParam String userId) {

        Post updatedPost = postService.removeReaction(postId, userId);
        return ResponseEntity.ok(updatedPost);
    }
}
