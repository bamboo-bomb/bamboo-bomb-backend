package com.bamboo.BambooBomb.repository;

import com.bamboo.BambooBomb.model.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends MongoRepository<Post, String>, PagingAndSortingRepository<Post, String> {
    // 작성자 아이디로 게시물을 검색하는 메서드
    Page<Post> findByAuthorId(String autherId, Pageable pageable);
}
