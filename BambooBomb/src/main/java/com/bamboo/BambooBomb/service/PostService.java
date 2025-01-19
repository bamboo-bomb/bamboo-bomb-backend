package com.bamboo.BambooBomb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bamboo.BambooBomb.model.Post;
import com.bamboo.BambooBomb.model.ReactionType;
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

    // 포스트 조회수 증가
    public Post incrementViewCount(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setViewCount(post.getViewCount() + 1);
        return postRepository.save(post);
    }

    // 포스트 조회(페이징 처리)
    public Page<Post> getPosts(String sortBy, int page, int size) {
        Pageable pageable;
        if ("views".equals(sortBy)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("viewCount"))); // 조회수 기준 내림차순 정렬
        } else if ("date".equals(sortBy)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("timestamp"))); // 최신 글 기준 내림차순 정렬
        } else {
            pageable = PageRequest.of(page, size); // 기본적으로 페이지네이션만 적용
        }

        return postRepository.findAll(pageable);
    }

    // 특정 포스트 조회(id)
    public Post findPostById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    // 포스트들 조회 (작성자)
    public Page<Post> getPostsByAuthorId(String authorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("timestamp"))); // 최근글 기준 내림차순 정렬
        return postRepository.findByAuthorId(authorId, pageable);
    }

    // 포스트 삭제
    public boolean deletePost(String id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 이모지 추가
    public Post addReaction(String postId, String userId, ReactionType reactionType) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        // 유저가 이미 반응을 한 이모지 -> 반응 변경(선택한 이모지 취소)
        if (post.getUserReactions().containsKey(userId)) {
            ReactionType existingReaction = post.getUserReactions().get(userId);
            post.getReactions().put(existingReaction, post.getReactions().get(existingReaction) - 1);
        }

        post.getUserReactions().put(userId, reactionType);
        post.getReactions().put(reactionType, post.getReactions().getOrDefault(reactionType, 0) + 1);

        return postRepository.save(post);
    }

    // 이모지 삭제
    public Post removeReaction(String postId, String userId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));

        // 유저가 반응을 했다면 반응을 삭제
        if (post.getUserReactions().containsKey(userId)) {
            ReactionType reactionType = post.getUserReactions().remove(userId);
            post.getReactions().put(reactionType, post.getReactions().get(reactionType) - 1);

            // 반응이 0이면 해당 반응을 맵에서 삭제
            if (post.getReactions().get(reactionType) <= 0) {
                post.getReactions().remove(reactionType);
            }
        }

        return postRepository.save(post);
    }
}
