package com.bamboo.BambooBomb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bamboo.BambooBomb.config.JwtTokenProvider;
import com.bamboo.BambooBomb.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login")
    public String login(@RequestParam String id, @RequestParam String email) {
        if (authService.authenticate(id, email)) {
            return jwtTokenProvider.generateToken(id);
        }
        throw new RuntimeException("Invalid credentials");
    }

}
