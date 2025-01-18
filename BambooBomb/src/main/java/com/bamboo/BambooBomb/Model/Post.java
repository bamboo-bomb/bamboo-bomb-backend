package com.bamboo.BambooBomb.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    @Field("expire_at")
    private Date expireAt; // 자동 삭제될 시간

    private String title;
    private String content;
    private LocalDateTime timestamp;
    private String authorId;

    // 감정표현 데이터 (key: 이모티콘 종류, value: 개수)
    private Map<ReactionType, Integer> reactions = new EnumMap<>(ReactionType.class);

    // 유저별 반응 추적 (key: 유저 ID, value: 누른 이모티콘)
    private Map<String, ReactionType> userReactions = new HashMap<>();

    public Post(String title, String content, String userId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.authorId = userId;
        initializeReactions();
    }

    // 이모지 기본값 초기화
    private void initializeReactions() {
        for (ReactionType type : ReactionType.values()) {
            reactions.put(type, 0);
        }
    }

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
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public Date getExpireAt() {
        return expireAt;
    }
    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }
    public String getAuthorId() {
        return authorId;
    }
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
    public Map<ReactionType, Integer> getReactions() {
        return reactions;
    }
    public void setReactions(Map<ReactionType, Integer> reactions) {
        this.reactions = reactions;
    }
    public Map<String, ReactionType> getUserReactions() {
        return userReactions;
    }
    public void setUserReactions(Map<String, ReactionType> userReactions) {
        this.userReactions = userReactions;
    }
}
