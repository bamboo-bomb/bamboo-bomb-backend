package com.bamboo.BambooBomb.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    @Indexed(name = "expire_at_index", expireAfterSeconds = 0)
    private LocalDateTime expireAt; // 자동 삭제될 시간

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getExpireAt() {
        return expireAt;
    }
    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }
    
}
