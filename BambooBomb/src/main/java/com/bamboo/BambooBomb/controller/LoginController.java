package com.bamboo.BambooBomb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

    // 네이버 로그인 후 바로 호출되는 주소
    @GetMapping("/login/callback")
    public ResponseEntity<Map<String, String>> loginSuccess(
        @RequestParam(value="state", required=false) String state, 
        @RequestParam(value="code", required=false) String code, 
        @RequestParam(value="error", required=false) String error, 
        @RequestParam(value="error_description", required=false) String error_description
        ) {
        Map<String, String> responseMap = new HashMap<>();
        if (error != null) {
            responseMap.put("error", error);
            responseMap.put("error_description", error_description);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }

        responseMap.put("state", state);
        responseMap.put("code", code);
        return ResponseEntity.ok(responseMap);
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

    // 토큰 삭제
    @GetMapping("/login/removeToken")
    public ResponseEntity<Map<String, String>> revokeAccessToken(
        @RequestParam(value = "access_token") String accessToken
    ) {
        Map<String, String> response = tokenService.revokeAccessToken(accessToken);
        return ResponseEntity.ok(response);
    }

    // 프로필 정보 (이메일, id)
    @GetMapping("/login/getProfile")
    public Map<String, Object> getUserInfo(
        @RequestParam("access_token") String accessToken
    ) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "https://openapi.naver.com/v1/nid/me";
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(response.getBody());
        }
        return response.getBody();
    }
}