package com.bamboo.BambooBomb.repository;

import com.bamboo.BambooBomb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    
}
