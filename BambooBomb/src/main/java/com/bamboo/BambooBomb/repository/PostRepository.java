package com.bamboo.BambooBomb.repository;

import com.bamboo.BambooBomb.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
    
}
