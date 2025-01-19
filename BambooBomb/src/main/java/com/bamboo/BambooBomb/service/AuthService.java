package com.bamboo.BambooBomb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bamboo.BambooBomb.model.User;
import com.bamboo.BambooBomb.repository.UserRepository;

// 로그인, JWT 토큰 생성 서비스
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    

    public boolean authenticate(String id, String email) {
        // DB에서 id로 유저 검색
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            return true;
        } else {
            User newUser = new User();
            newUser.setId(id);
            newUser.setEmail(email);
            userRepository.save(newUser);
            return true;
        }
    }
}
