package com.bamboo.BambooBomb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bamboo.BambooBomb.service.TokenService;


@RestController
public class LoginController {
    @Value("${CLIENT_ID}")
    private String clientId;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    private final TokenService tokenService;

    public LoginController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/login/callback")
    public String loginSuccess(
        @RequestParam(value="state", required=false) String state, 
        @RequestParam(value="code", required=false) String code, 
        @RequestParam(value="error", required=false) String error, 
        @RequestParam(value="error_description", required=false) String error_description
        ) {
        if (error != null) {
            return error_description;
        }
        if (state == null) {
            return "state is null";
        }
        if (code == null) {
            return "code is null";
        }

        return "Callback 성공! : " + state + code;
    }
    
    // 접근 토큰 발급 요청 url (GET / POST (json))
    // 토큰 발급
    @GetMapping("/login/getToken")
    public ResponseEntity<Map<String, String>> getAccessTokenTest(
        @RequestParam(value = "state") String state,
        @RequestParam(value = "code") String code
    ) {
        Map<String, String> response = tokenService.getAccessToken(code, state);
        return ResponseEntity.ok(response);
    }


    // 토큰 갱신
    @GetMapping("/login/refreshToken")
    public ResponseEntity<Map<String, String>> refreshAccessToken(
        @RequestParam(value = "refresh_token") String refreshToken
    ) {
        Map<String, String> response = tokenService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}