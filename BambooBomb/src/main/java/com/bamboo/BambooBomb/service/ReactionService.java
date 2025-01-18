package com.bamboo.BambooBomb.service;

import org.springframework.stereotype.Service;

import com.bamboo.BambooBomb.model.ReactionType;
import com.bamboo.BambooBomb.model.Post;

@Service
public class ReactionService {

    // 감정표현 추가 메서드
    public void addReaction(Post post, String userId, ReactionType emoji) {
        // 유저가 기존에 반응했는지 확인
        if (post.getUserReactions().containsKey(userId)) {
            ReactionType previousReaction = post.getUserReactions().get(userId);
            post.getReactions().put(previousReaction, post.getReactions().get(previousReaction) - 1);
        }

        // 새로운 반응 추가
        post.getUserReactions().put(userId, emoji);
        post.getReactions().put(emoji, post.getReactions().getOrDefault(emoji, 0) + 1);
    }

    // 감정표현 제거 메서드
    public void removeReaction(Post post, String userId) {
        if (post.getUserReactions().containsKey(userId)) {
            ReactionType reaction = post.getUserReactions().remove(userId);
            post.getReactions().put(reaction, post.getReactions().get(reaction) - 1);
        }
    }

    // 특정 이모티콘 개수 표시 (99+, 999+ 처리)
    public String getReactionCountDisplay(Post post, ReactionType emoji) {
        int count = post.getReactions().getOrDefault(emoji, 0);
        if (count >= 999) {
            return "999+";
        } else if (count >= 99) {
            return "99+";
        } else {
            return String.valueOf(count);
        }
    }
}
