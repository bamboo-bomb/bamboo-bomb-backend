package com.bamboo.BambooBomb.repository;

import com.bamboo.BambooBomb.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends MongoRepository<Post, String>, PagingAndSortingRepository<Post, String> {
    
}
