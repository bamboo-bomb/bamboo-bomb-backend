package com.bamboo.BambooBomb.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

import com.bamboo.BambooBomb.model.Post;

import jakarta.annotation.PostConstruct;

@Configuration
public class MongoConfig {
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        // TTL 인덱스 설정 (expireAt 필드를 기준으로 30분 후 자동 삭제)
        mongoTemplate.indexOps(Post.class)
                    .ensureIndex(new Index().on("expireAt", Sort.Direction.ASC).expire(30, TimeUnit.MINUTES));    }
        }
